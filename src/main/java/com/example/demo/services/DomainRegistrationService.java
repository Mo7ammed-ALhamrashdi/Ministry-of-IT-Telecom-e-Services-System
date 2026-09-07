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
public class DomainRegistrationService {

    private final DomainRegistrationRepository domainRepository;
    private final CitizenRepository citizenRepository;

    public DomainRegistrationService(
            DomainRegistrationRepository domainRepository,
            CitizenRepository citizenRepository) {

        this.domainRepository = domainRepository;
        this.citizenRepository = citizenRepository;
    }

    public DomainRegistrationDTO add(
            DomainRegistrationDTO dto) {

        Citizen citizen =
                findCitizenById(dto.getCitizenId());

        DomainRegistration domain =
                new DomainRegistration();

        domain.setDomainName(dto.getDomainName());
        domain.setRegisteredDate(dto.getRegisteredDate());
        domain.setExpiryDate(dto.getExpiryDate());
        domain.setStatus(dto.getStatus());
        domain.setCitizen(citizen);
        domain.setIsActive(true);
        domain.setCreatedDate(LocalDateTime.now());
        domain.setUpdatedDate(LocalDateTime.now());

        return DomainRegistrationDTO.convertToDTO(
                domainRepository.save(domain)
        );
    }

    public List<DomainRegistrationDTO> getAll() {

        List<DomainRegistration> domains =
                domainRepository.findAll()
                        .stream()
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

    public DomainRegistrationDTO getById(Long id) {

        return DomainRegistrationDTO.convertToDTO(
                findDomainById(id)
        );
    }

    public DomainRegistrationDTO update(
            Long id,
            DomainRegistrationDTO dto) {

        DomainRegistration domain =
                findDomainById(id);

        Citizen citizen =
                findCitizenById(dto.getCitizenId());

        domain.setDomainName(dto.getDomainName());
        domain.setRegisteredDate(dto.getRegisteredDate());
        domain.setExpiryDate(dto.getExpiryDate());
        domain.setStatus(dto.getStatus());
        domain.setCitizen(citizen);
        domain.setUpdatedDate(LocalDateTime.now());

        return DomainRegistrationDTO.convertToDTO(
                domainRepository.save(domain)
        );
    }

    public void delete(Long id) {

        DomainRegistration domain =
                findDomainById(id);

        domain.setIsActive(false);
        domain.setUpdatedDate(LocalDateTime.now());

        domainRepository.save(domain);
    }

    private DomainRegistration findDomainById(
            Long id) {

        DomainRegistration domain =
                domainRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Domain registration not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                domain.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Domain registration not found with id: " + id
            );
        }

        return domain;
    }

    private Citizen findCitizenById(Long id) {

        Citizen citizen =
                citizenRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Citizen not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                citizen.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Citizen not found with id: " + id
            );
        }

        return citizen;
    }
}