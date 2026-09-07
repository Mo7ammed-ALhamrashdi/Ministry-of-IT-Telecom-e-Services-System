package com.example.demo.dtos;

import com.example.demo.entities.Officer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private  Long id;

    @NotBlank(message = "Officer name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Designation must not exceed 100 characters")
    private String designation;

    @NotNull(message = "Department id is required")
    private Long departmentId;

    public static OfficerDTO convertToDTO(Officer officer) {
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

    public static List<OfficerDTO> convertToDTO(List<Officer> officers) {
        return officers.stream()
                .map(OfficerDTO::convertToDTO)
                .toList();
    }
}