package com.example.demo.controllers;

import com.example.demo.entities.Project;
import com.example.demo.services.ProjectService;
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
    public Project add(@RequestBody Project project) {

        return projectService.add(project);
    }

    @GetMapping("/getAll")
    public List<Project> getAll() {

        return projectService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Project getById(@PathVariable Long id) {

        return projectService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Project update(
            @PathVariable Long id,
            @RequestBody Project project) {

        return projectService.update(id, project);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        projectService.delete(id);

        return "Project deleted successfully";
    }
}