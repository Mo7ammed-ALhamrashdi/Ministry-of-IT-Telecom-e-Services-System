package com.example.demo.services;

import com.example.demo.dtos.ProjectDTO;
import com.example.demo.entities.Ministry;
import com.example.demo.entities.Project;
import com.example.demo.entities.Vendor;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.MinistryRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.VendorRepository;
import org.springframework.stereotype.Service;

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

        return ProjectDTO.convertToDTO(
                projectRepository.save(project)
        );
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

        return ProjectDTO.convertToDTO(
                findProjectById(id)
        );
    }

    public ProjectDTO update(
            Long id,
            ProjectDTO dto) {

        Project project =
                findProjectById(id);

        project.setTitle(dto.getTitle());
        project.setBudget(dto.getBudget());
        project.setStartDate(dto.getStartDate());
        project.setStatus(dto.getStatus());

        project.setMinistry(
                findMinistryById(dto.getMinistryId())
        );

        project.getVendors().clear();

        if (dto.getVendorIds() != null) {

            List<Vendor> vendors =
                    findVendors(dto.getVendorIds());

            project.getVendors().addAll(vendors);
        }

        project.setUpdatedDate(LocalDateTime.now());

        return ProjectDTO.convertToDTO(
                projectRepository.save(project)
        );
    }

    public void delete(Long id) {

        Project project =
                findProjectById(id);

        project.setIsActive(false);
        project.setUpdatedDate(LocalDateTime.now());

        projectRepository.save(project);
    }

    private Project findProjectById(Long id) {

        Project project =
                projectRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                project.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Project not found with id: " + id
            );
        }

        return project;
    }

    private Ministry findMinistryById(Long id) {

        Ministry ministry =
                ministryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ministry not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                ministry.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Ministry not found with id: " + id
            );
        }

        return ministry;
    }

    private List<Vendor> findVendors(
            List<Long> ids) {

        List<Vendor> vendors =
                new ArrayList<>();

        for (Long id : ids) {

            Vendor vendor =
                    vendorRepository.findById(id)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Vendor not found with id: " + id
                                    )
                            );

            if (!Boolean.TRUE.equals(
                    vendor.getIsActive())) {

                throw new ResourceNotFoundException(
                        "Vendor not found with id: " + id
                );
            }

            vendors.add(vendor);
        }

        return vendors;
    }
}