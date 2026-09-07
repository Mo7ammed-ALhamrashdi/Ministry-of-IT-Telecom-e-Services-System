package com.example.demo.services;

import com.example.demo.entities.Project;
import com.example.demo.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(
            ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    public Project add(Project project) {

        project.setId(null);
        project.setIsActive(true);
        project.setCreatedDate(LocalDateTime.now());
        project.setUpdatedDate(LocalDateTime.now());

        return projectRepository.save(project);
    }

    public List<Project> getAll() {

        return projectRepository.findAll()
                .stream()
                .filter(project ->
                        Boolean.TRUE.equals(
                                project.getIsActive()))
                .toList();
    }

    public Project getById(Long id) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Project not found"));

        if (!Boolean.TRUE.equals(project.getIsActive())) {
            throw new RuntimeException(
                    "Project not found");
        }

        return project;
    }

    public Project update(
            Long id,
            Project request) {

        Project project = getById(id);

        project.setTitle(request.getTitle());
        project.setBudget(request.getBudget());
        project.setStartDate(request.getStartDate());
        project.setStatus(request.getStatus());
        project.setMinistry(request.getMinistry());
        project.setVendors(request.getVendors());
        project.setUpdatedDate(LocalDateTime.now());

        return projectRepository.save(project);
    }

    public void delete(Long id) {

        Project project = getById(id);

        project.setIsActive(false);
        project.setUpdatedDate(LocalDateTime.now());

        projectRepository.save(project);
    }
}
