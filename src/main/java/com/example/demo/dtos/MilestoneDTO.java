package com.example.demo.dtos;

import com.example.demo.entities.Milestone;
import com.example.demo.enums.MilestoneStatus;
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
    private String title;
    private LocalDate dueDate;
    private MilestoneStatus status;
    private Long projectId;

    public static MilestoneDTO convertToDTO(
            Milestone milestone) {

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

    public static List<MilestoneDTO> convertToDTO(
            List<Milestone> milestones) {

        return milestones.stream()
                .map(MilestoneDTO::convertToDTO)
                .toList();
    }
}
