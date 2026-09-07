package com.example.demo.services;

import com.example.demo.entities.SpectrumLicense;
import com.example.demo.repositories.SpectrumLicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpectrumLicenseService {

    private final SpectrumLicenseRepository repository;

    public SpectrumLicenseService(
            SpectrumLicenseRepository repository) {

        this.repository = repository;
    }

    public SpectrumLicense add(
            SpectrumLicense spectrumLicense) {

        spectrumLicense.setId(null);
        spectrumLicense.setIsActive(true);
        spectrumLicense.setCreatedDate(
                LocalDateTime.now());
        spectrumLicense.setUpdatedDate(
                LocalDateTime.now());

        return repository.save(spectrumLicense);
    }

    public List<SpectrumLicense> getAll() {

        return repository.findAll()
                .stream()
                .filter(license ->
                        Boolean.TRUE.equals(
                                license.getIsActive()))
                .toList();
    }

    public SpectrumLicense getById(Long id) {

        SpectrumLicense license =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Spectrum license not found"));

        if (!Boolean.TRUE.equals(
                license.getIsActive())) {

            throw new RuntimeException(
                    "Spectrum license not found");
        }

        return license;
    }

    public SpectrumLicense update(
            Long id,
            SpectrumLicense request) {

        SpectrumLicense license = getById(id);

        license.setBandName(request.getBandName());
        license.setFrequencyMhz(
                request.getFrequencyMhz());
        license.setIssueDate(request.getIssueDate());
        license.setExpiryDate(request.getExpiryDate());
        license.setStatus(request.getStatus());
        license.setOperator(request.getOperator());
        license.setUpdatedDate(LocalDateTime.now());

        return repository.save(license);
    }

    public void delete(Long id) {

        SpectrumLicense license = getById(id);

        license.setIsActive(false);
        license.setUpdatedDate(LocalDateTime.now());

        repository.save(license);
    }
}

