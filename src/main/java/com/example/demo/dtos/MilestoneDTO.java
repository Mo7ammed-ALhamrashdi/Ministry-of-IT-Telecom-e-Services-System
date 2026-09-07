package com.example.demo.dtos;

import com.example.demo.entities.Milestone;
import com.example.demo.enums.MilestoneStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDTO {

    private Long id;

    @NotBlank(message = "Milestone title is required")
    @Size(max = 150, message = "Milestone title must not exceed 150 characters")
    private String title;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotNull(message = "Milestone status is required")
    private MilestoneStatus status;

    @NotNull(message = "Project id is required")
    private Long projectId;

    public static MilestoneDTO convertToDTO(Milestone milestone) {
        return MilestoneDTO.builder()
                .id(milestone.getId())
                .title(milestone.getTitle())
                .dueDate(milestone.getDueDate())
                .status(milestone.getStatus())
                .projectId(
                        milestone.getProject() != null
                                ? milestone.getProject().getId()
                                : null
                )
                .build();
    }

    public static List<MilestoneDTO> convertToDTO(List<Milestone> milestones) {
        return milestones.stream()
                .map(MilestoneDTO::convertToDTO)
                .toList();
    }
}