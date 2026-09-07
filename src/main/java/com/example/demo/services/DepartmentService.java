package com.example.demo.services;

import com.example.demo.entities.Department;
import com.example.demo.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    public Department add(Department department) {

        department.setId(null);
        department.setIsActive(true);
        department.setCreatedDate(LocalDateTime.now());
        department.setUpdatedDate(LocalDateTime.now());

        return departmentRepository.save(department);
    }

    public List<Department> getAll() {

        return departmentRepository.findAll()
                .stream()
                .filter(department ->
                        Boolean.TRUE.equals(
                                department.getIsActive()))
                .toList();
    }

    public Department getById(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found"));

        if (!Boolean.TRUE.equals(department.getIsActive())) {
            throw new RuntimeException("Department not found");
        }

        return department;
    }

    public Department update(
            Long id,
            Department request) {

        Department department = getById(id);

        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setMinistry(request.getMinistry());
        department.setUpdatedDate(LocalDateTime.now());

        return departmentRepository.save(department);
    }

    public void delete(Long id) {

        Department department = getById(id);

        department.setIsActive(false);
        department.setUpdatedDate(LocalDateTime.now());

        departmentRepository.save(department);
    }
}
