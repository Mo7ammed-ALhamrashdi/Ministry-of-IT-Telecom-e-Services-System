package com.example.demo.services;

import com.example.demo.dtos.CitizenDTO;
import com.example.demo.entities.Citizen;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CitizenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class   CitizenService {

    private final CitizenRepository citizenRepository;

    public CitizenService(
            CitizenRepository citizenRepository) {

        this.citizenRepository = citizenRepository;
    }

    public CitizenDTO add(CitizenDTO dto) {

        Citizen citizen = new Citizen();

        citizen.setName(dto.getName());
        citizen.setNationalId(dto.getNationalId());
        citizen.setPhoneNumber(dto.getPhoneNumber());
        citizen.setEmail(dto.getEmail());
        citizen.setIsActive(true);
        citizen.setCreatedDate(LocalDateTime.now());
        citizen.setUpdatedDate(LocalDateTime.now());

        return CitizenDTO.convertToDTO(
                citizenRepository.save(citizen)
        );
    }

    public List<CitizenDTO> getAll() {

        List<Citizen> citizens =
                citizenRepository.findAll()
                        .stream()
                        .filter(citizen ->
                                Boolean.TRUE.equals(
                                        citizen.getIsActive()
                                )
                        )
                        .toList();

        return CitizenDTO.convertToDTO(citizens);
    }

    public CitizenDTO getById(Long id) {

        return CitizenDTO.convertToDTO(
                findCitizenById(id)
        );
    }

    public CitizenDTO update(
            Long id,
            CitizenDTO dto) {

        Citizen citizen =
                findCitizenById(id);

        citizen.setName(dto.getName());
        citizen.setNationalId(dto.getNationalId());
        citizen.setPhoneNumber(dto.getPhoneNumber());
        citizen.setEmail(dto.getEmail());
        citizen.setUpdatedDate(LocalDateTime.now());

        return CitizenDTO.convertToDTO(
                citizenRepository.save(citizen)
        );
    }

    public void delete(Long id) {

        Citizen citizen =
                findCitizenById(id);

        citizen.setIsActive(false);
        citizen.setUpdatedDate(LocalDateTime.now());

        citizenRepository.save(citizen);
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