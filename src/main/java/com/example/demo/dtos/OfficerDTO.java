package com.example.demo.dtos;

import com.example.demo.entities.Officer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficerDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String designation;
    private Long departmentId;

    public static OfficerDTO convertToDTO(
            Officer officer) {

        return OfficerDTO.builder()
                .id(officer.getId())
                .name(officer.getName())
                .email(officer.getEmail())
                .phoneNumber(officer.getPhoneNumber())
                .designation(officer.getDesignation())
                .departmentId(
                        officer.getDepartment() != null
                                ? officer.getDepartment().getId()
                                : null
                )
                .build();
    }

    public static List<OfficerDTO> convertToDTO(
            List<Officer> officers) {

        return officers.stream()
                .map(OfficerDTO::convertToDTO)
                .toList();
    }
}