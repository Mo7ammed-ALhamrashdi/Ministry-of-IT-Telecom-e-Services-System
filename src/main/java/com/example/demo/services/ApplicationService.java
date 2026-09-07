package com.example.demo.services;

import com.example.demo.dtos.ApplicationDTO;
import com.example.demo.entities.Application;
import com.example.demo.entities.Citizen;
import com.example.demo.entities.Document;
import com.example.demo.entities.MinistryService;
import com.example.demo.entities.Officer;
import com.example.demo.enums.ApplicationStatus;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ApplicationRepository;
import com.example.demo.repositories.CitizenRepository;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.repositories.MinistryServiceRepository;
import com.example.demo.repositories.OfficerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class  ApplicationService {

    // Coordinates application submission, officer assignment, payment gating, and decision records.
    private final ApplicationRepository applicationRepository;
    private final CitizenRepository citizenRepository;
    private final MinistryServiceRepository ministryServiceRepository;
    private final OfficerRepository officerRepository;
    private final DocumentRepository documentRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CitizenRepository citizenRepository,
            MinistryServiceRepository ministryServiceRepository,
            OfficerRepository officerRepository,
            DocumentRepository documentRepository) {

        // Repositories are injected so this service can validate related records before saving applications.
        this.applicationRepository =
                applicationRepository;

        this.citizenRepository =
                citizenRepository;

        this.ministryServiceRepository =
                ministryServiceRepository;

        this.officerRepository =
                officerRepository;

        this.documentRepository =
                documentRepository;
    }

    public ApplicationDTO add(
            ApplicationDTO dto) {

        // The citizen must exist and be active before a new application can reference it.
        Citizen citizen =
                findCitizenById(
                        dto.getCitizenId()
                );

        MinistryService ministryService =
                findMinistryServiceById(
                        dto.getServiceId()
                );

        // DTO values are copied into a new entity while system-owned fields are set by the service.
        Application application =
                new Application();

        application.setApplicationDate(
                dto.getApplicationDate()
        );

        application.setStatus(
                ApplicationStatus.PENDING
        );

        // New applications always receive a generated reference number for later tracking.
        application.setReferenceNumber(
                generateReferenceNumber()
        );

        application.setCitizen(
                citizen
        );

        application.setService(
                ministryService
        );

        Officer officer =
                assignOfficer(
                        ministryService
                );

        application.setOfficer(
                officer
        );

        application.setIsActive(true);

        application.setCreatedDate(
                LocalDateTime.now()
        );

        application.setUpdatedDate(
                LocalDateTime.now()
        );

        Application savedApplication =
                applicationRepository.save(
                        application
                );

        return ApplicationDTO.convertToDTO(
                savedApplication
        );
    }

    public List<ApplicationDTO> getAll() {

        List<Application> applications =
                applicationRepository
                        .findAll()
                        .stream()
                        // Only active applications are exposed through normal read operations.
                        .filter(application ->
                                Boolean.TRUE.equals(
                                        application.getIsActive()
                                )
                        )
                        .toList();

        return ApplicationDTO.convertToDTO(
                applications
        );
    }

    public ApplicationDTO getById(
            Long id) {

        return ApplicationDTO.convertToDTO(
                findApplicationById(id)
        );
    }

    public ApplicationDTO update(
            Long id,
            ApplicationDTO dto) {

        Application application =
                findApplicationById(id);

        Citizen citizen =
                findCitizenById(
                        dto.getCitizenId()
                );

        MinistryService ministryService =
                findMinistryServiceById(
                        dto.getServiceId()
                );

        application.setApplicationDate(
                dto.getApplicationDate()
        );

        application.setCitizen(
                citizen
        );

        application.setService(
                ministryService
        );

        if (dto.getOfficerId() != null) {

            // Officer reassignment is optional and only happens when the DTO provides an officer id.
            application.setOfficer(
                    findOfficerById(
                            dto.getOfficerId()
                    )
            );
        }

        application.setUpdatedDate(
                LocalDateTime.now()
        );

        Application updatedApplication =
                applicationRepository.save(
                        application
                );

        return ApplicationDTO.convertToDTO(
                updatedApplication
        );
    }

    public List<ApplicationDTO>
    getByStatus(
            ApplicationStatus status) {

        List<Application> applications =
                applicationRepository
                        .findApplicationsByStatus(
                                status
                        );

        return ApplicationDTO.convertToDTO(
                applications
        );
    }

    public List<ApplicationDTO>
    getCitizenHistory(
            Long citizenId) {

        findCitizenById(citizenId);

        List<Application> applications =
                applicationRepository
                        .findCitizenApplicationHistory(
                                citizenId
                        );

        return ApplicationDTO.convertToDTO(
                applications
        );
    }

    public ApplicationDTO approve(
            Long id) {

        Application application =
                findApplicationById(id);

        // Approval is blocked until an active PAID payment is attached to the application.
        verifyApplicationPaid(
                application
        );

        application.setStatus(
                ApplicationStatus.APPROVED
        );

        application.setUpdatedDate(
                LocalDateTime.now()
        );

        Application savedApplication =
                applicationRepository.save(
                        application
                );

        createDecisionDocument(
                savedApplication,
                "APPROVED"
        );

        return ApplicationDTO.convertToDTO(
                savedApplication
        );
    }

    public ApplicationDTO reject(
            Long id) {

        Application application =
                findApplicationById(id);

        verifyApplicationPaid(
                application
        );

        application.setStatus(
                ApplicationStatus.REJECTED
        );

        application.setUpdatedDate(
                LocalDateTime.now()
        );

        Application savedApplication =
                applicationRepository.save(
                        application
                );

        createDecisionDocument(
                savedApplication,
                "REJECTED"
        );

        return ApplicationDTO.convertToDTO(
                savedApplication
        );
    }

    public void delete(Long id) {

        Application application =
                findApplicationById(id);

        // Soft delete preserves the application row while hiding it from active lookups.
        application.setIsActive(false);

        application.setUpdatedDate(
                LocalDateTime.now()
        );

        applicationRepository.save(
                application
        );
    }

    private String generateReferenceNumber() {

        return "APP-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private Officer assignOfficer(
            MinistryService ministryService) {

        // A service without a department cannot be routed to a responsible officer.
        if (ministryService.getDepartment()
                == null) {

            throw new IllegalArgumentException(
                    "Service does not belong to a department"
            );
        }

        List<Officer> officers =
                officerRepository
                        .findActiveOfficersByDepartment(
                                ministryService
                                        .getDepartment()
                                        .getId()
                        );

        if (officers.isEmpty()) {

            throw new IllegalArgumentException(
                    "No active officer available for this service"
            );
        }

        return officers.get(0);
    }

    private void verifyApplicationPaid(
            Application application) {

        if (application.getPayment() == null) {

            throw new IllegalArgumentException(
                    "Application must be paid before processing"
            );
        }

        if (!Boolean.TRUE.equals(
                application.getPayment()
                        .getIsActive())) {

            throw new IllegalArgumentException(
                    "Application payment is inactive"
            );
        }

        if (!PaymentStatus.PAID.equals(
                application
                        .getPayment()
                        .getStatus())) {

            throw new IllegalArgumentException(
                    "Application must be paid before processing"
            );
        }
    }

    private void createDecisionDocument(
            Application application,
            String decision) {

        // A decision document is created after approval or rejection for the application record.
        Document document =
                new Document();

        document.setTitle(
                "Application Decision - "
                        + application
                        .getReferenceNumber()
        );

        document.setType(
                "DECISION_"
                        + decision
        );

        document.setUploadDate(
                LocalDate.now()
        );

        document.setApplication(
                application
        );

        document.setProject(null);

        document.setIsActive(true);

        document.setCreatedDate(
                LocalDateTime.now()
        );

        document.setUpdatedDate(
                LocalDateTime.now()
        );

        documentRepository.save(
                document
        );
    }

    private Application findApplicationById(
            Long id) {

        Application application =
                applicationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                application.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Application not found with id: "
                            + id
            );
        }

        return application;
    }

    private Citizen findCitizenById(
            Long id) {

        Citizen citizen =
                citizenRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Citizen not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                citizen.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Citizen not found with id: "
                            + id
            );
        }

        return citizen;
    }

    private MinistryService
    findMinistryServiceById(
            Long id) {

        MinistryService ministryService =
                ministryServiceRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Service not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                ministryService
                        .getIsActive())) {

            throw new ResourceNotFoundException(
                    "Service not found with id: "
                            + id
            );
        }

        return ministryService;
    }

    private Officer findOfficerById(
            Long id) {

        Officer officer =
                officerRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Officer not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                officer.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Officer not found with id: "
                            + id
            );
        }

        return officer;
    }
}
