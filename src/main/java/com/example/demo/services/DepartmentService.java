package com.example.demo.services;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.entities.Department;
import com.example.demo.entities.Ministry;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.MinistryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  DepartmentService {

    // Maintains departments and their required parent ministry relationship.
    private final DepartmentRepository departmentRepository;
    private final MinistryRepository ministryRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            MinistryRepository ministryRepository) {

        this.departmentRepository = departmentRepository;
        this.ministryRepository = ministryRepository;
    }

    public DepartmentDTO add(DepartmentDTO dto) {

        // The parent ministry is resolved first so the new department points to an active ministry.
        Ministry ministry =
                findMinistryById(dto.getMinistryId());

        Department department =
                new Department();

        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setMinistry(ministry);
        department.setIsActive(true);
        department.setCreatedDate(LocalDateTime.now());
        department.setUpdatedDate(LocalDateTime.now());

        Department saved =
                departmentRepository.save(department);

        return DepartmentDTO.convertToDTO(saved);
    }

    public List<DepartmentDTO> getAll() {

        List<Department> departments =
                departmentRepository.findAll()
                        .stream()
                        .filter(department ->
                                Boolean.TRUE.equals(
                                        department.getIsActive()
                                )
                        )
                        .toList();

        return DepartmentDTO.convertToDTO(departments);
    }

    public DepartmentDTO getById(Long id) {

        return DepartmentDTO.convertToDTO(
                findDepartmentById(id)
        );
    }

    public DepartmentDTO update(
            Long id,
            DepartmentDTO dto) {

        Department department =
                findDepartmentById(id);

        Ministry ministry =
                findMinistryById(dto.getMinistryId());

        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setMinistry(ministry);
        department.setUpdatedDate(LocalDateTime.now());

        Department updated =
                departmentRepository.save(department);

        return DepartmentDTO.convertToDTO(updated);
    }

    public void delete(Long id) {

        Department department =
                findDepartmentById(id);

        // Soft delete disables the department without physically deleting it.
        department.setIsActive(false);
        department.setUpdatedDate(LocalDateTime.now());

        departmentRepository.save(department);
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

    private Ministry findMinistryById(Long id) {

        // Related ministries must be active before department creation or updates can use them.
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
