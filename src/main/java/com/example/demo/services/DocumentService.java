package com.example.demo.services;

import com.example.demo.dtos.DocumentDTO;
import com.example.demo.entities.Application;
import com.example.demo.entities.Document;
import com.example.demo.entities.Project;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ApplicationRepository;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  DocumentService {

    // Manages uploaded document metadata for either applications or projects.
    private final DocumentRepository documentRepository;
    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;

    public DocumentService(
            DocumentRepository documentRepository,
            ApplicationRepository applicationRepository,
            ProjectRepository projectRepository) {

        this.documentRepository = documentRepository;
        this.applicationRepository = applicationRepository;
        this.projectRepository = projectRepository;
    }

    public DocumentDTO add(DocumentDTO dto) {

        // A document must have exactly one parent before it is converted into an entity.
        validateDocumentParent(dto);

        Document document = new Document();

        document.setTitle(dto.getTitle());
        document.setType(dto.getType());
        document.setUploadDate(dto.getUploadDate());

        if (dto.getApplicationId() != null) {
            // Application documents are linked only after confirming the application is active.
            document.setApplication(
                    findApplicationById(
                            dto.getApplicationId()
                    )
            );
        }

        if (dto.getProjectId() != null) {
            document.setProject(
                    findProjectById(
                            dto.getProjectId()
                    )
            );
        }

        document.setIsActive(true);
        document.setCreatedDate(LocalDateTime.now());
        document.setUpdatedDate(LocalDateTime.now());

        return DocumentDTO.convertToDTO(
                documentRepository.save(document)
        );
    }

    public List<DocumentDTO> getAll() {

        List<Document> documents =
                documentRepository.findAll()
                        .stream()
                        // Soft-deleted documents are omitted from normal document listings.
                        .filter(document ->
                                Boolean.TRUE.equals(
                                        document.getIsActive()
                                )
                        )
                        .toList();

        return DocumentDTO.convertToDTO(
                documents
        );
    }

    public DocumentDTO getById(Long id) {

        return DocumentDTO.convertToDTO(
                findDocumentById(id)
        );
    }

    public DocumentDTO update(
            Long id,
            DocumentDTO dto) {

        validateDocumentParent(dto);

        Document document =
                findDocumentById(id);

        // Parent links are reset so the update cannot leave both relationships attached.
        document.setTitle(dto.getTitle());
        document.setType(dto.getType());
        document.setUploadDate(dto.getUploadDate());

        document.setApplication(null);
        document.setProject(null);

        if (dto.getApplicationId() != null) {

            document.setApplication(
                    findApplicationById(
                            dto.getApplicationId()
                    )
            );
        }

        if (dto.getProjectId() != null) {

            document.setProject(
                    findProjectById(
                            dto.getProjectId()
                    )
            );
        }

        document.setUpdatedDate(LocalDateTime.now());

        return DocumentDTO.convertToDTO(
                documentRepository.save(document)
        );
    }

    public void delete(Long id) {

        Document document =
                findDocumentById(id);

        document.setIsActive(false);
        document.setUpdatedDate(LocalDateTime.now());

        documentRepository.save(document);
    }

    private void validateDocumentParent(
            DocumentDTO dto) {

        // Documents are modeled as belonging to either one application or one project, never neither.
        if (dto.getApplicationId() == null
                && dto.getProjectId() == null) {

            throw new IllegalArgumentException(
                    "Document must belong to an application or project"
            );
        }

        if (dto.getApplicationId() != null
                && dto.getProjectId() != null) {

            throw new IllegalArgumentException(
                    "Document cannot belong to application and project at the same time"
            );
        }
    }

    private Document findDocumentById(Long id) {

        Document document =
                documentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Document not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                document.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Document not found with id: " + id
            );
        }

        return document;
    }

    private Application findApplicationById(
            Long id) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                application.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Application not found with id: " + id
            );
        }

        return application;
    }

    private Project findProjectById(Long id) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                project.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Project not found with id: " + id
            );
        }

        return project;
    }
}
