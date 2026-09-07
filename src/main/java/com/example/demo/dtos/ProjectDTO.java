package com.example.demo.dtos;

import com.example.demo.entities.Project;
import com.example.demo.entities.Vendor;
import com.example.demo.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;

    @NotBlank(message = "Project title is required")
    @Size(max = 150, message = "Project title must not exceed 150 characters")
    private String title;

    @NotNull(message = "Budget is required")
    @PositiveOrZero(message = "Budget cannot be negative")
    private BigDecimal budget;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Project status is required")
    private ProjectStatus status;

    @NotNull(message = "Ministry id is required")
    private Long ministryId;

    private List<Long> vendorIds;

    public static ProjectDTO convertToDTO(Project project) {

        List<Long> vendors = project.getVendors() == null
                ? Collections.emptyList()
                : project.getVendors()
                .stream()
                .map(Vendor::getId)
                .toList();

        return ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .budget(project.getBudget())
                .startDate(project.getStartDate())
                .status(project.getStatus())
                .ministryId(
                        project.getMinistry() != null
                                ? project.getMinistry().getId()
                                : null
                )
                .vendorIds(vendors)
                .build();
    }

    public static List<ProjectDTO> convertToDTO(List<Project> projects) {
        return projects.stream()
                .map(ProjectDTO::convertToDTO)
                .toList();
    }
}