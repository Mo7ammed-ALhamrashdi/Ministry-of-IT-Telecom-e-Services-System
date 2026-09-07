package com.example.demo.controllers;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
// Handles HTTP requests for department records in the ministry system.
public class DepartmentController {

    // DepartmentService contains the department business logic used by these endpoints.
    private final DepartmentService departmentService;

    // Constructor dependency injection avoids manual service creation inside the controller.
    public DepartmentController(
            DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @PostMapping("/add")
    public DepartmentDTO add(
            @Valid @RequestBody DepartmentDTO dto) {

        return departmentService.add(dto);
    }

    // Lists departments through the service layer and returns DTOs to API clients.
    @GetMapping("/getAll")
    public List<DepartmentDTO> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public DepartmentDTO getById(
            @PathVariable Long id) {

        return departmentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public DepartmentDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO dto) {

        return  departmentService.update(id, dto);
    }

    // Delegates deletion to the service, which decides how the record is removed.
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        departmentService.delete(id);

        return "Department deleted successfully";
    }
}
