package com.example.demo.services;

import com.example.demo.entities.Citizen;
import com.example.demo.repositories.CitizenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    public CitizenService(
            CitizenRepository citizenRepository) {

        this.citizenRepository = citizenRepository;
    }

    public Citizen add(Citizen citizen) {

        citizen.setId(null);
        citizen.setIsActive(true);
        citizen.setCreatedDate(LocalDateTime.now());
        citizen.setUpdatedDate(LocalDateTime.now());

        return citizenRepository.save(citizen);
    }

    public List<Citizen> getAll() {

        return citizenRepository.findAll()
                .stream()
                .filter(citizen ->
                        Boolean.TRUE.equals(
                                citizen.getIsActive()))
                .toList();
    }

    public Citizen getById(Long id) {

        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Citizen not found"));

        if (!Boolean.TRUE.equals(citizen.getIsActive())) {
            throw new RuntimeException("Citizen not found");
        }

        return citizen;
    }

    public Citizen update(Long id, Citizen request) {

        Citizen citizen = getById(id);

        citizen.setName(request.getName());
        citizen.setNationalId(request.getNationalId());
        citizen.setPhoneNumber(request.getPhoneNumber());
        citizen.setEmail(request.getEmail());
        citizen.setUpdatedDate(LocalDateTime.now());

        return citizenRepository.save(citizen);
    }

    public void delete(Long id) {

        Citizen citizen = getById(id);

        citizen.setIsActive(false);
        citizen.setUpdatedDate(LocalDateTime.now());

        citizenRepository.save(citizen);
    }
}