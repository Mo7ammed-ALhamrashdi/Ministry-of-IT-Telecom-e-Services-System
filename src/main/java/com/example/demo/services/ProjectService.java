package com.example.demo.services;

import com.example.demo.dtos.ProjectDTO;
import com.example.demo.entities.Ministry;
import com.example.demo.entities.Project;
import com.example.demo.entities.Vendor;
import com.example.demo.enums.MilestoneStatus;
import com.example.demo.enums.ProjectStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.MinistryRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MinistryRepository ministryRepository;
    private final VendorRepository vendorRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            MinistryRepository ministryRepository,
            VendorRepository vendorRepository) {

        this.projectRepository = projectRepository;
        this.ministryRepository = ministryRepository;
        this.vendorRepository = vendorRepository;
    }

    public ProjectDTO add(ProjectDTO dto) {

        Ministry ministry =
                findMinistryById(dto.getMinistryId());

        Project project = new Project();

        project.setTitle(dto.getTitle());
        project.setBudget(dto.getBudget());
        project.setStartDate(dto.getStartDate());
        project.setStatus(dto.getStatus());
        project.setMinistry(ministry);

        if (dto.getVendorIds() != null) {

            List<Vendor> vendors =
                    findVendors(dto.getVendorIds());

            project.getVendors().addAll(vendors);
        }

        project.setIsActive(true);
        project.setCreatedDate(LocalDateTime.now());
        project.setUpdatedDate(LocalDateTime.now());

        Project savedProject =
                projectRepository.save(project);

        return ProjectDTO.convertToDTO(savedProject);
    }

    public List<ProjectDTO> getAll() {

        List<Project> projects =
                projectRepository.findAll()
                        .stream()
                        .filter(project ->
                                Boolean.TRUE.equals(
                                        project.getIsActive()
                                )
                        )
                        .toList();

        return ProjectDTO.convertToDTO(projects);
    }

    public ProjectDTO getById(Long id) {

        Project project = findProjectById(id);

        return ProjectDTO.convertToDTO(project);
    }

    public ProjectDTO update(
            Long id,
            ProjectDTO dto) {

        Project project =
                findProjectById(id);

        Ministry ministry =
                findMinistryById(dto.getMinistryId());

        project.setTitle(dto.getTitle());
        project.setBudget(dto.getBudget());
        project.setStartDate(dto.getStartDate());
        project.setStatus(dto.getStatus());
        project.setMinistry(ministry);

        project.getVendors().clear();

        if (dto.getVendorIds() != null) {

            List<Vendor> vendors =
                    findVendors(dto.getVendorIds());

            project.getVendors().addAll(vendors);
        }

        project.setUpdatedDate(LocalDateTime.now());

        Project updatedProject =
                projectRepository.save(project);

        return ProjectDTO.convertToDTO(updatedProject);
    }

    public List<ProjectDTO> getProjectsAboveBudget(
            BigDecimal budget) {

        if (budget == null ||
                budget.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Budget cannot be negative"
            );
        }

        List<Project> projects =
                projectRepository
                        .findProjectsAboveBudget(budget);

        return ProjectDTO.convertToDTO(projects);
    }

    public Double getMilestoneProgress(
            Long projectId) {

        Project project =
                findProjectById(projectId);

        List<com.example.demo.entities.Milestone>
                activeMilestones =
                project.getMilestones()
                        .stream()
                        .filter(milestone ->
                                Boolean.TRUE.equals(
                                        milestone.getIsActive()
                                )
                        )
                        .toList();

        if (activeMilestones.isEmpty()) {
            return 0.0;
        }

        long completed =
                activeMilestones.stream()
                        .filter(milestone ->
                                MilestoneStatus.COMPLETED
                                        .equals(
                                                milestone.getStatus()
                                        )
                        )
                        .count();

        return (completed * 100.0)
                / activeMilestones.size();
    }

    public ProjectDTO completeProject(
            Long projectId) {

        Project project =
                findProjectById(projectId);

        List<com.example.demo.entities.Milestone>
                activeMilestones =
                project.getMilestones()
                        .stream()
                        .filter(milestone ->
                                Boolean.TRUE.equals(
                                        milestone.getIsActive()
                                )
                        )
                        .toList();

        if (activeMilestones.isEmpty()) {

            throw new IllegalArgumentException(
                    "Project has no active milestones"
            );
        }

        boolean allCompleted =
                activeMilestones.stream()
                        .allMatch(milestone ->
                                MilestoneStatus.COMPLETED
                                        .equals(
                                                milestone.getStatus()
                                        )
                        );

        if (!allCompleted) {

            throw new IllegalArgumentException(
                    "All milestones must be completed before completing the project"
            );
        }

        project.setStatus(
                ProjectStatus.COMPLETED
        );

        project.setUpdatedDate(
                LocalDateTime.now()
        );

        Project savedProject =
                projectRepository.save(project);

        return ProjectDTO.convertToDTO(
                savedProject
        );
    }

    public void delete(Long id) {

        Project project =
                findProjectById(id);

        project.setIsActive(false);

        project.setUpdatedDate(
                LocalDateTime.now()
        );

        projectRepository.save(project);
    }

    private Project findProjectById(
            Long id) {

        Project project =
                projectRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                project.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Project not found with id: "
                            + id
            );
        }

        return project;
    }

    private Ministry findMinistryById(
            Long id) {

        Ministry ministry =
                ministryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ministry not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                ministry.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Ministry not found with id: "
                            + id
            );
        }

        return ministry;
    }

    private List<Vendor> findVendors(
            List<Long> vendorIds) {

        List<Vendor> vendors =
                new ArrayList<>();

        for (Long vendorId : vendorIds) {

            Vendor vendor =
                    vendorRepository
                            .findById(vendorId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Vendor not found with id: "
                                                    + vendorId
                                    )
                            );

            if (!Boolean.TRUE.equals(
                    vendor.getIsActive())) {

                throw new ResourceNotFoundException(
                        "Vendor not found with id: "
                                + vendorId
                );
            }

            vendors.add(vendor);
        }

        return vendors;
    }
}