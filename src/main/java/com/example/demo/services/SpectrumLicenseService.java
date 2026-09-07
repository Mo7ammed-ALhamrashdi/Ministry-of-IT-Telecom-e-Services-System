package com.example.demo.services;

import com.example.demo.dtos.SpectrumLicenseDTO;
import com.example.demo.entities.Operator;
import com.example.demo.entities.SpectrumLicense;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.OperatorRepository;
import com.example.demo.repositories.SpectrumLicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpectrumLicenseService {

    private final SpectrumLicenseRepository spectrumLicenseRepository;
    private final OperatorRepository operatorRepository;

    public SpectrumLicenseService(
            SpectrumLicenseRepository spectrumLicenseRepository,
            OperatorRepository operatorRepository) {

        this.spectrumLicenseRepository =
                spectrumLicenseRepository;

        this.operatorRepository =
                operatorRepository;
    }

    public SpectrumLicenseDTO add(
            SpectrumLicenseDTO dto) {

        Operator operator =
                findOperatorById(
                        dto.getOperatorId()
                );

        validateDates(
                dto.getIssueDate(),
                dto.getExpiryDate()
        );

        SpectrumLicense spectrumLicense =
                new SpectrumLicense();

        spectrumLicense.setBandName(
                dto.getBandName()
        );

        spectrumLicense.setFrequencyMhz(
                dto.getFrequencyMhz()
        );

        spectrumLicense.setIssueDate(
                dto.getIssueDate()
        );

        spectrumLicense.setExpiryDate(
                dto.getExpiryDate()
        );

        spectrumLicense.setStatus(
                dto.getStatus()
        );

        spectrumLicense.setOperator(
                operator
        );

        spectrumLicense.setIsActive(true);

        spectrumLicense.setCreatedDate(
                LocalDateTime.now()
        );

        spectrumLicense.setUpdatedDate(
                LocalDateTime.now()
        );

        SpectrumLicense savedLicense =
                spectrumLicenseRepository.save(
                        spectrumLicense
                );

        return SpectrumLicenseDTO.convertToDTO(
                savedLicense
        );
    }

    public List<SpectrumLicenseDTO> getAll() {

        List<SpectrumLicense> licenses =
                spectrumLicenseRepository
                        .findAll()
                        .stream()
                        .filter(license ->
                                Boolean.TRUE.equals(
                                        license.getIsActive()
                                )
                        )
                        .toList();

        return SpectrumLicenseDTO.convertToDTO(
                licenses
        );
    }

    public SpectrumLicenseDTO getById(
            Long id) {

        SpectrumLicense license =
                findLicenseById(id);

        return SpectrumLicenseDTO.convertToDTO(
                license
        );
    }

    public SpectrumLicenseDTO update(
            Long id,
            SpectrumLicenseDTO dto) {

        SpectrumLicense license =
                findLicenseById(id);

        Operator operator =
                findOperatorById(
                        dto.getOperatorId()
                );

        validateDates(
                dto.getIssueDate(),
                dto.getExpiryDate()
        );

        license.setBandName(
                dto.getBandName()
        );

        license.setFrequencyMhz(
                dto.getFrequencyMhz()
        );

        license.setIssueDate(
                dto.getIssueDate()
        );

        license.setExpiryDate(
                dto.getExpiryDate()
        );

        license.setStatus(
                dto.getStatus()
        );

        license.setOperator(
                operator
        );

        license.setUpdatedDate(
                LocalDateTime.now()
        );

        SpectrumLicense updatedLicense =
                spectrumLicenseRepository.save(
                        license
                );

        return SpectrumLicenseDTO.convertToDTO(
                updatedLicense
        );
    }

    public List<SpectrumLicenseDTO>
    getExpiringWithin30Days() {

        LocalDate today =
                LocalDate.now();

        LocalDate endDate =
                today.plusDays(30);

        List<SpectrumLicense> licenses =
                spectrumLicenseRepository
                        .findExpiringLicenses(
                                today,
                                endDate
                        );

        return SpectrumLicenseDTO.convertToDTO(
                licenses
        );
    }

    public void delete(Long id) {

        SpectrumLicense license =
                findLicenseById(id);

        license.setIsActive(false);

        license.setUpdatedDate(
                LocalDateTime.now()
        );

        spectrumLicenseRepository.save(
                license
        );
    }

    private SpectrumLicense findLicenseById(
            Long id) {

        SpectrumLicense license =
                spectrumLicenseRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Spectrum license not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                license.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Spectrum license not found with id: "
                            + id
            );
        }

        return license;
    }

    private Operator findOperatorById(
            Long id) {

        Operator operator =
                operatorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Operator not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                operator.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Operator not found with id: "
                            + id
            );
        }

        return operator;
    }

    private void validateDates(
            LocalDate issueDate,
            LocalDate expiryDate) {

        if (issueDate == null ||
                expiryDate == null) {

            throw new IllegalArgumentException(
                    "Issue date and expiry date are required"
            );
        }

        if (!expiryDate.isAfter(issueDate)) {

            throw new IllegalArgumentException(
                    "Expiry date must be after issue date"
            );
        }
    }
}