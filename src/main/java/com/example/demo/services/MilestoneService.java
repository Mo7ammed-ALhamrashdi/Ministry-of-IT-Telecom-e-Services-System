package com.example.demo.services;

import com.example.demo.dtos.MilestoneDTO;
import com.example.demo.entities.Milestone;
import com.example.demo.entities.Project;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.MilestoneRepository;
import com.example.demo.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public MilestoneService(
            MilestoneRepository milestoneRepository,
            ProjectRepository projectRepository) {

        this.milestoneRepository =
                milestoneRepository;

        this.projectRepository =
                projectRepository;
    }

    public MilestoneDTO add(
            MilestoneDTO dto) {

        Project project =
                findProjectById(
                        dto.getProjectId()
                );

        Milestone milestone =
                new Milestone();

        milestone.setTitle(
                dto.getTitle()
        );

        milestone.setDueDate(
                dto.getDueDate()
        );

        milestone.setStatus(
                dto.getStatus()
        );

        milestone.setProject(
                project
        );

        milestone.setIsActive(true);

        milestone.setCreatedDate(
                LocalDateTime.now()
        );

        milestone.setUpdatedDate(
                LocalDateTime.now()
        );

        Milestone savedMilestone =
                milestoneRepository.save(
                        milestone
                );

        return MilestoneDTO.convertToDTO(
                savedMilestone
        );
    }

    public List<MilestoneDTO> getAll() {

        List<Milestone> milestones =
                milestoneRepository
                        .findAll()
                        .stream()
                        .filter(milestone ->
                                Boolean.TRUE.equals(
                                        milestone.getIsActive()
                                )
                        )
                        .toList();

        return MilestoneDTO.convertToDTO(
                milestones
        );
    }

    public MilestoneDTO getById(
            Long id) {

        return MilestoneDTO.convertToDTO(
                findMilestoneById(id)
        );
    }

    public MilestoneDTO update(
            Long id,
            MilestoneDTO dto) {

        Milestone milestone =
                findMilestoneById(id);

        Project project =
                findProjectById(
                        dto.getProjectId()
                );

        milestone.setTitle(
                dto.getTitle()
        );

        milestone.setDueDate(
                dto.getDueDate()
        );

        milestone.setStatus(
                dto.getStatus()
        );

        milestone.setProject(
                project
        );

        milestone.setUpdatedDate(
                LocalDateTime.now()
        );

        Milestone updatedMilestone =
                milestoneRepository.save(
                        milestone
                );

        return MilestoneDTO.convertToDTO(
                updatedMilestone
        );
    }

    public void delete(
            Long id) {

        Milestone milestone =
                findMilestoneById(id);

        milestone.setIsActive(false);

        milestone.setUpdatedDate(
                LocalDateTime.now()
        );

        milestoneRepository.save(
                milestone
        );
    }

    private Milestone findMilestoneById(
            Long id) {

        Milestone milestone =
                milestoneRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Milestone not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                milestone.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Milestone not found with id: "
                            + id
            );
        }

        return milestone;
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
}