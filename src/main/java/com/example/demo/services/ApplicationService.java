package com.example.demo.services;

import com.example.demo.entities.Application;
import com.example.demo.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository) {

        this.applicationRepository =
                applicationRepository;
    }

    public Application add(Application application) {

        application.setId(null);
        application.setIsActive(true);
        application.setCreatedDate(LocalDateTime.now());
        application.setUpdatedDate(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    public List<Application> getAll() {

        return applicationRepository.findAll()
                .stream()
                .filter(application ->
                        Boolean.TRUE.equals(
                                application.getIsActive()))
                .toList();
    }

    public Application getById(Long id) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"));

        if (!Boolean.TRUE.equals(
                application.getIsActive())) {

            throw new RuntimeException(
                    "Application not found");
        }

        return application;
    }

    public Application update(
            Long id,
            Application request) {

        Application application = getById(id);

        application.setApplicationDate(
                request.getApplicationDate());
        application.setStatus(request.getStatus());
        application.setReferenceNumber(
                request.getReferenceNumber());
        application.setCitizen(request.getCitizen());
        application.setService(request.getService());
        application.setOfficer(request.getOfficer());
        application.setUpdatedDate(
                LocalDateTime.now());

        return applicationRepository.save(application);
    }

    public void delete(Long id) {

        Application application = getById(id);

        application.setIsActive(false);
        application.setUpdatedDate(
                LocalDateTime.now());

        applicationRepository.save(application);
    }
}
