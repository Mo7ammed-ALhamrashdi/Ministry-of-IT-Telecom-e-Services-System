package com.example.demo.services;

import com.example.demo.entities.DomainRegistration;
import com.example.demo.repositories.DomainRegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DomainRegistrationService {

    private final DomainRegistrationRepository repository;

    public DomainRegistrationService(
            DomainRegistrationRepository repository) {

        this.repository = repository;
    }

    public DomainRegistration add(
            DomainRegistration domainRegistration) {

        domainRegistration.setId(null);
        domainRegistration.setIsActive(true);
        domainRegistration.setCreatedDate(
                LocalDateTime.now());
        domainRegistration.setUpdatedDate(
                LocalDateTime.now());

        return repository.save(domainRegistration);
    }

    public List<DomainRegistration> getAll() {

        return repository.findAll()
                .stream()
                .filter(domain ->
                        Boolean.TRUE.equals(
                                domain.getIsActive()))
                .toList();
    }

    public DomainRegistration getById(Long id) {

        DomainRegistration domain =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Domain registration not found"));

        if (!Boolean.TRUE.equals(
                domain.getIsActive())) {

            throw new RuntimeException(
                    "Domain registration not found");
        }

        return domain;
    }

    public DomainRegistration update(
            Long id,
            DomainRegistration request) {

        DomainRegistration domain = getById(id);

        domain.setDomainName(
                request.getDomainName());
        domain.setRegisteredDate(
                request.getRegisteredDate());
        domain.setExpiryDate(
                request.getExpiryDate());
        domain.setStatus(request.getStatus());
        domain.setCitizen(request.getCitizen());
        domain.setUpdatedDate(
                LocalDateTime.now());

        return repository.save(domain);
    }

    public void delete(Long id) {

        DomainRegistration domain = getById(id);

        domain.setIsActive(false);
        domain.setUpdatedDate(LocalDateTime.now());

        repository.save(domain);
    }
}