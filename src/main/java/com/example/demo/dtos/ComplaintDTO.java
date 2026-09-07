package com.example.demo.dtos;

import com.example.demo.entities.Complaint;
import com.example.demo.enums.ComplaintStatus;
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
    private String subject;
    private String description;
    private ComplaintStatus status;
    private LocalDate filedDate;

    private Long citizenId;
    private Long operatorId;
    private Long officerId;

    public static ComplaintDTO convertToDTO(
            Complaint complaint) {

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

    public static List<ComplaintDTO> convertToDTO(
            List<Complaint> complaints) {

        return complaints.stream()
                .map(ComplaintDTO::convertToDTO)
                .toList();
    }
}