package com.example.demo.services;

import com.example.demo.dtos.OfficerDTO;
import com.example.demo.entities.Department;
import com.example.demo.entities.Officer;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.OfficerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  OfficerService {

    // Manages ministry officers and keeps each officer assigned to an active department.
    private final OfficerRepository officerRepository;
    private final DepartmentRepository departmentRepository;

    public OfficerService(
            OfficerRepository officerRepository,
            DepartmentRepository departmentRepository) {

        this.officerRepository = officerRepository;
        this.departmentRepository = departmentRepository;
    }

    public OfficerDTO add(OfficerDTO dto) {

        // Officer creation resolves the department id from the DTO into a managed entity.
        Department department =
                findDepartmentById(dto.getDepartmentId());

        Officer officer = new Officer();

        officer.setName(dto.getName());
        officer.setEmail(dto.getEmail());
        officer.setPhoneNumber(dto.getPhoneNumber());
        officer.setDesignation(dto.getDesignation());
        officer.setDepartment(department);
        officer.setIsActive(true);
        officer.setCreatedDate(LocalDateTime.now());
        officer.setUpdatedDate(LocalDateTime.now());

        Officer saved =
                officerRepository.save(officer);

        return OfficerDTO.convertToDTO(saved);
    }

    public List<OfficerDTO> getAll() {

        List<Officer> officers =
                officerRepository.findAll()
                        .stream()
                        // Inactive officers are excluded from normal officer listings.
                        .filter(officer ->
                                Boolean.TRUE.equals(
                                        officer.getIsActive()
                                )
                        )
                        .toList();

        return OfficerDTO.convertToDTO(officers);
    }

    public OfficerDTO getById(Long id) {

        return OfficerDTO.convertToDTO(
                findOfficerById(id)
        );
    }

    public OfficerDTO update(
            Long id,
            OfficerDTO dto) {

        Officer officer =
                findOfficerById(id);

        Department department =
                findDepartmentById(dto.getDepartmentId());

        officer.setName(dto.getName());
        officer.setEmail(dto.getEmail());
        officer.setPhoneNumber(dto.getPhoneNumber());
        officer.setDesignation(dto.getDesignation());
        officer.setDepartment(department);
        officer.setUpdatedDate(LocalDateTime.now());

        return OfficerDTO.convertToDTO(
                officerRepository.save(officer)
        );
    }

    public void delete(Long id) {

        Officer officer =
                findOfficerById(id);

        // Soft delete prevents future assignment while retaining the officer record.
        officer.setIsActive(false);
        officer.setUpdatedDate(LocalDateTime.now());

        officerRepository.save(officer);
    }

    private Officer findOfficerById(Long id) {

        Officer officer =
                officerRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Officer not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                officer.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Officer not found with id: " + id
            );
        }

        return officer;
    }

    private Department findDepartmentById(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                department.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Department not found with id: " + id
            );
        }

        return department;
    }
}
