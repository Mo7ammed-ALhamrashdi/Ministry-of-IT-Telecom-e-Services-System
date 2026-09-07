package com.example.demo.dtos;

import com.example.demo.entities.Citizen;
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
    private String name;
    private String nationalId;
    private String phoneNumber;
    private String email;

    public static CitizenDTO convertToDTO(
            Citizen citizen) {

        return CitizenDTO.builder()
                .id(citizen.getId())
                .name(citizen.getName())
                .nationalId(citizen.getNationalId())
                .phoneNumber(citizen.getPhoneNumber())
                .email(citizen.getEmail())
                .build();
    }

    public static List<CitizenDTO> convertToDTO(
            List<Citizen> citizens) {

        return citizens.stream()
                .map(CitizenDTO::convertToDTO)
                .toList();
    }
}
