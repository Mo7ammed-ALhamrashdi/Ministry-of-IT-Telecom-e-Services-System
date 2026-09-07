package com.example.demo.controllers;

import com.example.demo.entities.Department;
import com.example.demo.services.DepartmentService;
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
    public Department add(
            @RequestBody Department department) {

        return departmentService.add(department);
    }

    @GetMapping("/getAll")
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Department getById(@PathVariable Long id) {

        return departmentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Department update(
            @PathVariable Long id,
            @RequestBody Department department) {

        return departmentService.update(id, department);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        departmentService.delete(id);

        return "Department deleted successfully";
    }
}
