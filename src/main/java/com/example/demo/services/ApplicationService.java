package com.example.demo.services;

import com.example.demo.dtos.ApplicationDTO;
import com.example.demo.entities.Application;
import com.example.demo.entities.Citizen;
import com.example.demo.entities.MinistryService;
import com.example.demo.entities.Officer;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ApplicationRepository;
import com.example.demo.repositories.CitizenRepository;
import com.example.demo.repositories.MinistryServiceRepository;
import com.example.demo.repositories.OfficerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CitizenRepository citizenRepository;
    private final MinistryServiceRepository ministryServiceRepository;
    private final OfficerRepository officerRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CitizenRepository citizenRepository,
            MinistryServiceRepository ministryServiceRepository,
            OfficerRepository officerRepository) {

        this.applicationRepository = applicationRepository;
        this.citizenRepository = citizenRepository;
        this.ministryServiceRepository = ministryServiceRepository;
        this.officerRepository = officerRepository;
    }

    public ApplicationDTO add(ApplicationDTO dto) {

        Citizen citizen =
                findCitizenById(dto.getCitizenId());

        MinistryService ministryService =
                findMinistryServiceById(dto.getServiceId());

        Application application =
                new Application();

        application.setApplicationDate(
                dto.getApplicationDate()
        );

        application.setStatus(
                dto.getStatus()
        );

        application.setReferenceNumber(
                dto.getReferenceNumber()
        );

        application.setCitizen(
                citizen
        );

        application.setService(
                ministryService
        );

        if (dto.getOfficerId() != null) {

            Officer officer =
                    findOfficerById(
                            dto.getOfficerId()
                    );

            application.setOfficer(
                    officer
            );
        }

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
                applicationRepository.findAll()
                        .stream()
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

    public ApplicationDTO getById(Long id) {

        Application application =
                findApplicationById(id);

        return ApplicationDTO.convertToDTO(
                application
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

        application.setStatus(
                dto.getStatus()
        );

        application.setReferenceNumber(
                dto.getReferenceNumber()
        );

        application.setCitizen(
                citizen
        );

        application.setService(
                ministryService
        );

        if (dto.getOfficerId() != null) {

            Officer officer =
                    findOfficerById(
                            dto.getOfficerId()
                    );

            application.setOfficer(
                    officer
            );

        } else {

            application.setOfficer(
                    null
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

    public void delete(Long id) {

        Application application =
                findApplicationById(id);

        application.setIsActive(false);

        application.setUpdatedDate(
                LocalDateTime.now()
        );

        applicationRepository.save(
                application
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

    private MinistryService findMinistryServiceById(
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
                ministryService.getIsActive())) {

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