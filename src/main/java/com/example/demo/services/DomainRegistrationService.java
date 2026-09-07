package com.example.demo.services;

import com.example.demo.dtos.DomainRegistrationDTO;
import com.example.demo.entities.Citizen;
import com.example.demo.entities.DomainRegistration;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CitizenRepository;
import com.example.demo.repositories.DomainRegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  DomainRegistrationService {

    // Owns domain registration, renewal, duplicate checks, and citizen ownership links.
    private final DomainRegistrationRepository domainRegistrationRepository;
    private final CitizenRepository citizenRepository;

    public DomainRegistrationService(
            DomainRegistrationRepository domainRegistrationRepository,
            CitizenRepository citizenRepository) {

        this.domainRegistrationRepository =
                domainRegistrationRepository;

        this.citizenRepository =
                citizenRepository;
    }

    public DomainRegistrationDTO add(
            DomainRegistrationDTO dto) {

        Citizen citizen =
                findCitizenById(
                        dto.getCitizenId()
                );

        // Active domain names are unique regardless of letter casing.
        domainRegistrationRepository
                .findByDomainNameIgnoreCaseAndIsActiveTrue(
                        dto.getDomainName()
                )
                .ifPresent(domain -> {
                    throw new IllegalArgumentException(
                            "Domain name is already registered and active"
                    );
                });

        DomainRegistration domainRegistration =
                new DomainRegistration();

        // The DTO supplies domain dates and status while the service attaches ownership metadata.
        domainRegistration.setDomainName(
                dto.getDomainName()
        );

        domainRegistration.setRegisteredDate(
                dto.getRegisteredDate()
        );

        domainRegistration.setExpiryDate(
                dto.getExpiryDate()
        );

        domainRegistration.setStatus(
                dto.getStatus()
        );

        domainRegistration.setCitizen(
                citizen
        );

        domainRegistration.setIsActive(true);

        domainRegistration.setCreatedDate(
                LocalDateTime.now()
        );

        domainRegistration.setUpdatedDate(
                LocalDateTime.now()
        );

        DomainRegistration savedDomain =
                domainRegistrationRepository.save(
                        domainRegistration
                );

        return DomainRegistrationDTO.convertToDTO(
                savedDomain
        );
    }

    public List<DomainRegistrationDTO> getAll() {

        List<DomainRegistration> domains =
                domainRegistrationRepository
                        .findAll()
                        .stream()
                        // Soft-deleted registrations are filtered out of public service results.
                        .filter(domain ->
                                Boolean.TRUE.equals(
                                        domain.getIsActive()
                                )
                        )
                        .toList();

        return DomainRegistrationDTO.convertToDTO(
                domains
        );
    }

    public DomainRegistrationDTO getById(
            Long id) {

        DomainRegistration domain =
                findDomainById(id);

        return DomainRegistrationDTO.convertToDTO(
                domain
        );
    }

    public DomainRegistrationDTO update(
            Long id,
            DomainRegistrationDTO dto) {

        DomainRegistration domain =
                findDomainById(id);

        Citizen citizen =
                findCitizenById(
                        dto.getCitizenId()
                );

        domainRegistrationRepository
                .findByDomainNameIgnoreCaseAndIsActiveTrue(
                        dto.getDomainName()
                )
                .ifPresent(existingDomain -> {

                    // Updating may keep the same domain name but cannot take another active registration's name.
                    if (!existingDomain
                            .getId()
                            .equals(id)) {

                        throw new IllegalArgumentException(
                                "Domain name is already registered and active"
                        );
                    }
                });

        domain.setDomainName(
                dto.getDomainName()
        );

        domain.setRegisteredDate(
                dto.getRegisteredDate()
        );

        domain.setExpiryDate(
                dto.getExpiryDate()
        );

        domain.setStatus(
                dto.getStatus()
        );

        domain.setCitizen(
                citizen
        );

        domain.setUpdatedDate(
                LocalDateTime.now()
        );

        DomainRegistration updatedDomain =
                domainRegistrationRepository.save(
                        domain
                );

        return DomainRegistrationDTO.convertToDTO(
                updatedDomain
        );
    }

    public DomainRegistrationDTO renew(
            Long id,
            DomainRegistrationDTO dto) {

        DomainRegistration domain =
                findDomainById(id);

        // Renewal changes only the expiry date and update timestamp for the existing registration.
        domain.setExpiryDate(
                dto.getExpiryDate()
        );

        domain.setUpdatedDate(
                LocalDateTime.now()
        );

        DomainRegistration renewedDomain =
                domainRegistrationRepository.save(
                        domain
                );

        return DomainRegistrationDTO.convertToDTO(
                renewedDomain
        );
    }

    public void delete(Long id) {

        DomainRegistration domain =
                findDomainById(id);

        domain.setIsActive(false);

        domain.setUpdatedDate(
                LocalDateTime.now()
        );

        domainRegistrationRepository.save(
                domain
        );
    }

    private DomainRegistration findDomainById(
            Long id) {

        DomainRegistration domain =
                domainRegistrationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Domain registration not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                domain.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Domain registration not found with id: "
                            + id
            );
        }

        return domain;
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
}
