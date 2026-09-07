package com.example.demo.services;

import com.example.demo.dtos.MinistryDTO;
import com.example.demo.entities.Ministry;
import com.example.demo.exceptions.ResourceNotFoundException;
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

    public MinistryDTO add(MinistryDTO dto) {

        Ministry ministry = new Ministry();

        ministry.setName(dto.getName());
        ministry.setAddress(dto.getAddress());
        ministry.setIsActive(true);
        ministry.setCreatedDate(LocalDateTime.now());
        ministry.setUpdatedDate(LocalDateTime.now());

        Ministry saved = ministryRepository.save(ministry);

        return MinistryDTO.convertToDTO(saved);
    }

    public List<MinistryDTO> getAll() {

        List<Ministry> ministries =
                ministryRepository.findAll()
                        .stream()
                        .filter(ministry ->
                                Boolean.TRUE.equals(
                                        ministry.getIsActive()
                                )
                        )
                        .toList();

        return MinistryDTO.convertToDTO(ministries);
    }

    public MinistryDTO getById(Long id) {
        return MinistryDTO.convertToDTO(
                findMinistryById(id)
        );
    }

    public MinistryDTO update(
            Long id,
            MinistryDTO dto) {

        Ministry ministry =
                findMinistryById(id);

        ministry.setName(dto.getName());
        ministry.setAddress(dto.getAddress());
        ministry.setUpdatedDate(LocalDateTime.now());

        Ministry updated =
                ministryRepository.save(ministry);

        return MinistryDTO.convertToDTO(updated);
    }

    public void delete(Long id) {

        Ministry ministry =
                findMinistryById(id);

        ministry.setIsActive(false);
        ministry.setUpdatedDate(LocalDateTime.now());

        ministryRepository.save(ministry);
    }

    private Ministry findMinistryById(Long id) {

        Ministry ministry =
                ministryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ministry not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                ministry.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Ministry not found with id: " + id
            );
        }

        return ministry;
    }
}