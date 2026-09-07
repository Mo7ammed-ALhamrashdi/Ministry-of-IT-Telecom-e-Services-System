package com.example.demo.controllers;

import com.example.demo.dtos.ProjectDTO;
import com.example.demo.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(
            ProjectService projectService) {

        this.projectService = projectService;
    }

    @PostMapping("/add")
    public ProjectDTO add(
            @Valid @RequestBody ProjectDTO dto) {

        return projectService.add(dto);
    }

    @GetMapping("/getAll")
    public List<ProjectDTO> getAll() {
        return projectService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ProjectDTO getById(
            @PathVariable Long id) {

        return projectService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ProjectDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO dto) {

        return projectService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        projectService.delete(id);

        return "Project deleted successfully";
    }
}