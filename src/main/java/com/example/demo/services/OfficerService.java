package com.example.demo.services;

import com.example.demo.entities.Officer;
import com.example.demo.repositories.OfficerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfficerService {

    private final OfficerRepository officerRepository;

    public OfficerService(
            OfficerRepository officerRepository) {

        this.officerRepository = officerRepository;
    }

    public Officer add(Officer officer) {

        officer.setId(null);
        officer.setIsActive(true);
        officer.setCreatedDate(LocalDateTime.now());
        officer.setUpdatedDate(LocalDateTime.now());

        return officerRepository.save(officer);
    }

    public List<Officer> getAll() {

        return officerRepository.findAll()
                .stream()
                .filter(officer ->
                        Boolean.TRUE.equals(
                                officer.getIsActive()))
                .toList();
    }

    public Officer getById(Long id) {

        Officer officer = officerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Officer not found"));

        if (!Boolean.TRUE.equals(officer.getIsActive())) {
            throw new RuntimeException("Officer not found");
        }

        return officer;
    }

    public Officer update(Long id, Officer request) {

        Officer officer = getById(id);

        officer.setName(request.getName());
        officer.setEmail(request.getEmail());
        officer.setPhoneNumber(request.getPhoneNumber());
        officer.setDesignation(request.getDesignation());
        officer.setDepartment(request.getDepartment());
        officer.setUpdatedDate(LocalDateTime.now());

        return officerRepository.save(officer);
    }

    public void delete(Long id) {

        Officer officer = getById(id);

        officer.setIsActive(false);
        officer.setUpdatedDate(LocalDateTime.now());

        officerRepository.save(officer);
    }
}
