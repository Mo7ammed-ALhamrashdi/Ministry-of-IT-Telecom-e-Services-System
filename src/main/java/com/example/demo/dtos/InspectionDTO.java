package com.example.demo.dtos;

import com.example.demo.entities.Inspection;
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
public class InspectionDTO {

    private Long id;
    private LocalDate inspectionDate;
    private String result;
    private String notes;

    private Long operatorId;
    private Long officerId;

    public static InspectionDTO convertToDTO(
            Inspection inspection) {

        return InspectionDTO.builder()
                .id(inspection.getId())
                .inspectionDate(
                        inspection.getInspectionDate())
                .result(inspection.getResult())
                .notes(inspection.getNotes())
                .operatorId(
                        inspection.getOperator() != null
                                ? inspection.getOperator().getId()
                                : null
                )
                .officerId(
                        inspection.getOfficer() != null
                                ? inspection.getOfficer().getId()
                                : null
                )
                .build();
    }

    public static List<InspectionDTO> convertToDTO(
            List<Inspection> inspections) {

        return inspections.stream()
                .map(InspectionDTO::convertToDTO)
                .toList();
    }
}