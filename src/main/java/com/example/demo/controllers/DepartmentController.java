package com.example.demo.controllers;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @PostMapping("/add")
    public DepartmentDTO add(
            @Valid @RequestBody DepartmentDTO dto) {

        return departmentService.add(dto);
    }

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

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        departmentService.delete(id);

        return "Department deleted successfully";
    }
}