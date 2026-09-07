package com.example.demo.dtos;

import com.example.demo.entities.Inspection;
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
public class InspectionDTO {

    private Long id;

    @NotNull(message = "Inspection date is required")
    private LocalDate inspectionDate;

    @Size(max = 200, message = "Result must not exceed 200 characters")
    private String result;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    @NotNull(message = "Operator id is required")
    private Long operatorId;

    @NotNull(message = "Officer id is required")
    private Long officerId;

    public static InspectionDTO convertToDTO(Inspection inspection) {
        return InspectionDTO.builder()
                .id(inspection.getId())
                .inspectionDate(inspection.getInspectionDate())
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

    public static List<InspectionDTO> convertToDTO(List<Inspection> inspections) {
        return inspections.stream()
                .map(InspectionDTO::convertToDTO)
                .toList();
    }
}