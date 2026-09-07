package com.example.demo.services;

import com.example.demo.entities.Ministry;
import com.example.demo.repositories.MinistryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MinistryService {

    private final MinistryRepository ministryRepository;

    public MinistryService(MinistryRepository ministryRepository) {
        this.ministryRepository = ministryRepository;
    }

    public Ministry add(Ministry ministry) {

        ministry.setId(null);
        ministry.setIsActive(true);
        ministry.setCreatedDate(LocalDateTime.now());
        ministry.setUpdatedDate(LocalDateTime.now());

        return ministryRepository.save(ministry);
    }

    public List<Ministry> getAll() {

        return ministryRepository.findAll()
                .stream()
                .filter(ministry ->
                        Boolean.TRUE.equals(ministry.getIsActive()))
                .toList();
    }

    public Ministry getById(Long id) {

        Ministry ministry = ministryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ministry not found"));

        if (!Boolean.TRUE.equals(ministry.getIsActive())) {
            throw new RuntimeException("Ministry not found");
        }

        return ministry;
    }

    public Ministry update(Long id, Ministry request) {

        Ministry ministry = getById(id);

        ministry.setName(request.getName());
        ministry.setAddress(request.getAddress());
        ministry.setUpdatedDate(LocalDateTime.now());

        return ministryRepository.save(ministry);
    }

    public void delete(Long id) {

        Ministry ministry = getById(id);

        ministry.setIsActive(false);
        ministry.setUpdatedDate(LocalDateTime.now());

        ministryRepository.save(ministry);
    }
}