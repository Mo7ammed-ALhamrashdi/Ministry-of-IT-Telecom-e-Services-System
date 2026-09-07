package com.example.demo.controllers;

import com.example.demo.dtos.ProjectDTO;
import com.example.demo.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/project")
// Handles REST requests for ministry projects and project-specific reports.
public class ProjectController {

    // ProjectService owns project business rules such as progress and completion.
    private final ProjectService projectService;

    // Constructor injection provides the controller with its required service collaborator.
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

    // The budget path variable is converted to BigDecimal for the service query.
    @GetMapping("/aboveBudget/{budget}")
    public List<ProjectDTO> getAboveBudget(
            @PathVariable BigDecimal budget) {

        return projectService
                .getProjectsAboveBudget(budget);
    }

    // Returns milestone progress for a project by asking the service to calculate it.
    @GetMapping("/progress/{id}")
    public Double getProgress(
            @PathVariable Long id) {

        return projectService
                .getMilestoneProgress(id);
    }

    // Completing a project is a state-changing business action, so it uses PUT.
    @PutMapping("/complete/{id}")
    public ProjectDTO complete(
            @PathVariable Long id) {

        return projectService
                .completeProject(id);
    }

    @PutMapping("/update/{id}")
    public ProjectDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO dto) {

        return projectService.update(
                id,
                dto
        );
    }

    @DeleteMapping("/delete/{id}")
    public  String delete(
            @PathVariable Long id) {

        projectService.delete(id);

        return "Project deleted successfully";
    }
}
