package com.example.demo.dtos;

import com.example.demo.entities.Complaint;
import com.example.demo.enums.ComplaintStatus;
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
public class ComplaintDTO {

    private Long id;

    @NotBlank(message = "Subject is required")
    @Size(max = 150, message = "Subject must not exceed 150 characters")
    private String subject;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Complaint status is required")
    private ComplaintStatus status;

    @NotNull(message = "Filed date is required")
    private LocalDate filedDate;

    @NotNull(message = "Citizen id is required")
    private Long citizenId;

    @NotNull(message = "Operator id is required")
    private Long operatorId;

    private Long officerId;

    public static ComplaintDTO convertToDTO(Complaint complaint) {
        return ComplaintDTO.builder()
                .id(complaint.getId())
                .subject(complaint.getSubject())
                .description(complaint.getDescription())
                .status(complaint.getStatus())
                .filedDate(complaint.getFiledDate())
                .citizenId(
                        complaint.getCitizen() != null
                                ? complaint.getCitizen().getId()
                                : null
                )
                .operatorId(
                        complaint.getOperator() != null
                                ? complaint.getOperator().getId()
                                : null
                )
                .officerId(
                         complaint.getOfficer() != null
                                ? complaint.getOfficer().getId()
                                : null
                )
                .build();
    }

    public static List<ComplaintDTO> convertToDTO(List<Complaint> complaints) {
        return complaints.stream()
                .map(ComplaintDTO::convertToDTO)
                .toList();
    }
}