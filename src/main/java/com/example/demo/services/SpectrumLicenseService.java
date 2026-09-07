package com.example.demo.services;

import com.example.demo.dtos.SpectrumLicenseDTO;
import com.example.demo.entities.Operator;
import com.example.demo.entities.SpectrumLicense;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.OperatorRepository;
import com.example.demo.repositories.SpectrumLicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpectrumLicenseService {

    private final SpectrumLicenseRepository licenseRepository;
    private final OperatorRepository operatorRepository;

    public SpectrumLicenseService(
            SpectrumLicenseRepository licenseRepository,
            OperatorRepository operatorRepository) {

        this.licenseRepository = licenseRepository;
        this.operatorRepository = operatorRepository;
    }

    public SpectrumLicenseDTO add(
            SpectrumLicenseDTO dto) {

        Operator operator =
                findOperatorById(dto.getOperatorId());

        SpectrumLicense license =
                new SpectrumLicense();

        license.setBandName(dto.getBandName());
        license.setFrequencyMhz(dto.getFrequencyMhz());
        license.setIssueDate(dto.getIssueDate());
        license.setExpiryDate(dto.getExpiryDate());
        license.setStatus(dto.getStatus());
        license.setOperator(operator);
        license.setIsActive(true);
        license.setCreatedDate(LocalDateTime.now());
        license.setUpdatedDate(LocalDateTime.now());

        return SpectrumLicenseDTO.convertToDTO(
                licenseRepository.save(license)
        );
    }

    public List<SpectrumLicenseDTO> getAll() {

        List<SpectrumLicense> licenses =
                licenseRepository.findAll()
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

    public SpectrumLicenseDTO getById(Long id) {

        return SpectrumLicenseDTO.convertToDTO(
                findLicenseById(id)
        );
    }

    public SpectrumLicenseDTO update(
            Long id,
            SpectrumLicenseDTO dto) {

        SpectrumLicense license =
                findLicenseById(id);

        Operator operator =
                findOperatorById(dto.getOperatorId());

        license.setBandName(dto.getBandName());
        license.setFrequencyMhz(dto.getFrequencyMhz());
        license.setIssueDate(dto.getIssueDate());
        license.setExpiryDate(dto.getExpiryDate());
        license.setStatus(dto.getStatus());
        license.setOperator(operator);
        license.setUpdatedDate(LocalDateTime.now());

        return SpectrumLicenseDTO.convertToDTO(
                licenseRepository.save(license)
        );
    }

    public void delete(Long id) {

        SpectrumLicense license =
                findLicenseById(id);

        license.setIsActive(false);
        license.setUpdatedDate(LocalDateTime.now());

        licenseRepository.save(license);
    }

    private SpectrumLicense findLicenseById(Long id) {

        SpectrumLicense license =
                licenseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Spectrum license not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                license.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Spectrum license not found with id: " + id
            );
        }

        return license;
    }

    private Operator findOperatorById(Long id) {

        Operator operator =
                operatorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Operator not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                operator.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Operator not found with id: " + id
            );
        }

        return operator;
    }
}