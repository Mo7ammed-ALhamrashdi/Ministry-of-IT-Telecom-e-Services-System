package com.example.demo.services;

import com.example.demo.entities.MinistryService;
import com.example.demo.repositories.MinistryServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GovernmentServiceService {

    private final MinistryServiceRepository repository;

    public GovernmentServiceService(
            MinistryServiceRepository repository) {

        this.repository = repository;
    }

    public MinistryService add(
            MinistryService ministryService) {

        ministryService.setId(null);
        ministryService.setIsActive(true);
        ministryService.setCreatedDate(
                LocalDateTime.now());
        ministryService.setUpdatedDate(
                LocalDateTime.now());

        return repository.save(ministryService);
    }

    public List<MinistryService> getAll() {

        return repository.findAll()
                .stream()
                .filter(service ->
                        Boolean.TRUE.equals(
                                service.getIsActive()))
                .toList();
    }

    public MinistryService getById(Long id) {

        MinistryService ministryService =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service not found"));

        if (!Boolean.TRUE.equals(
                ministryService.getIsActive())) {

            throw new RuntimeException(
                    "Service not found");
        }

        return ministryService;
    }

    public MinistryService update(
            Long id,
            MinistryService request) {

        MinistryService ministryService =
                getById(id);

        ministryService.setName(request.getName());
        ministryService.setDescription(
                request.getDescription());
        ministryService.setFee(request.getFee());
        ministryService.setProcessingDays(
                request.getProcessingDays());
        ministryService.setDepartment(
                request.getDepartment());

        ministryService.setUpdatedDate(
                LocalDateTime.now());

        return repository.save(ministryService);
    }

    public void delete(Long id) {

        MinistryService ministryService =
                getById(id);

        ministryService.setIsActive(false);
        ministryService.setUpdatedDate(
                LocalDateTime.now());

        repository.save(ministryService);
    }
}
