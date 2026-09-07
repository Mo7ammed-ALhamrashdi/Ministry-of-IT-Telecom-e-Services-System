package com.example.demo.services;

import com.example.demo.dtos.MinistryServiceDTO;
import com.example.demo.entities.Department;
import com.example.demo.entities.MinistryService;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.MinistryServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GovernmentServiceService {

    private final MinistryServiceRepository serviceRepository;
    private final DepartmentRepository departmentRepository;

    public GovernmentServiceService(
            MinistryServiceRepository serviceRepository,
            DepartmentRepository departmentRepository) {

        this.serviceRepository = serviceRepository;
        this.departmentRepository = departmentRepository;
    }

    public MinistryServiceDTO add(
            MinistryServiceDTO dto) {

        Department department =
                findDepartmentById(dto.getDepartmentId());

        MinistryService service =
                new MinistryService();

        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setFee(dto.getFee());
        service.setProcessingDays(dto.getProcessingDays());
        service.setDepartment(department);
        service.setIsActive(true);
        service.setCreatedDate(LocalDateTime.now());
        service.setUpdatedDate(LocalDateTime.now());

        return MinistryServiceDTO.convertToDTO(
                serviceRepository.save(service)
        );
    }

    public List<MinistryServiceDTO> getAll() {

        List<MinistryService> services =
                serviceRepository.findAll()
                        .stream()
                        .filter(service ->
                                Boolean.TRUE.equals(
                                        service.getIsActive()
                                )
                        )
                        .toList();

        return MinistryServiceDTO.convertToDTO(
                services
        );
    }

    public MinistryServiceDTO getById(Long id) {

        return MinistryServiceDTO.convertToDTO(
                findServiceById(id)
        );
    }

    public MinistryServiceDTO update(
            Long id,
            MinistryServiceDTO dto) {

        MinistryService service =
                findServiceById(id);

        Department department =
                findDepartmentById(dto.getDepartmentId());

        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setFee(dto.getFee());
        service.setProcessingDays(dto.getProcessingDays());
        service.setDepartment(department);
        service.setUpdatedDate(LocalDateTime.now());

        return MinistryServiceDTO.convertToDTO(
                serviceRepository.save(service)
        );
    }

    public void delete(Long id) {

        MinistryService service =
                findServiceById(id);

        service.setIsActive(false);
        service.setUpdatedDate(LocalDateTime.now());

        serviceRepository.save(service);
    }

    private MinistryService findServiceById(Long id) {

        MinistryService service =
                serviceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Service not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                service.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Service not found with id: " + id
            );
        }

        return service;
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