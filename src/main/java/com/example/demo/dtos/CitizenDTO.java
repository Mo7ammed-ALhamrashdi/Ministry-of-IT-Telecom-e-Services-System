package com.example.demo.dtos;

import com.example.demo.entities.Citizen;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CitizenDTO {

    private Long id;

    @NotBlank(message = "Citizen name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "National ID is required")
    @Size(max = 30, message = "National ID must not exceed 30 characters")
    private String nationalId;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    public static CitizenDTO convertToDTO(Citizen citizen) {
        return CitizenDTO.builder()
                .id(citizen.getId())
                .name(citizen.getName())
                .nationalId(citizen.getNationalId())
                .phoneNumber(citizen.getPhoneNumber())
                .email(citizen.getEmail())
                .build();
    }

    public static List<CitizenDTO> convertToDTO(List<Citizen> citizens) {
        return citizens.stream()
                .map(CitizenDTO::convertToDTO)
                .toList();
    }
}