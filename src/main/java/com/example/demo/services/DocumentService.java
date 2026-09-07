package com.example.demo.services;

import com.example.demo.entities.Document;
import com.example.demo.repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(
            DocumentRepository documentRepository) {

        this.documentRepository =
                documentRepository;
    }

    public Document add(Document document) {

        document.setId(null);
        document.setIsActive(true);
        document.setCreatedDate(LocalDateTime.now());
        document.setUpdatedDate(LocalDateTime.now());

        return documentRepository.save(document);
    }

    public List<Document> getAll() {

        return documentRepository.findAll()
                .stream()
                .filter(document ->
                        Boolean.TRUE.equals(
                                document.getIsActive()))
                .toList();
    }

    public Document getById(Long id) {

        Document document =
                documentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found"));

        if (!Boolean.TRUE.equals(
                document.getIsActive())) {

            throw new RuntimeException(
                    "Document not found");
        }

        return document;
    }

    public Document update(
            Long id,
            Document request) {

        Document document = getById(id);

        document.setTitle(request.getTitle());
        document.setType(request.getType());
        document.setUploadDate(
                request.getUploadDate());
        document.setApplication(
                request.getApplication());
        document.setProject(
                request.getProject());
        document.setUpdatedDate(
                LocalDateTime.now());

        return documentRepository.save(document);
    }

    public void delete(Long id) {

        Document document = getById(id);

        document.setIsActive(false);
        document.setUpdatedDate(
                LocalDateTime.now());

        documentRepository.save(document);
    }
}