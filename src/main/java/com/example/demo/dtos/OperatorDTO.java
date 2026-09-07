package com.example.demo.dtos;

import com.example.demo.entities.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDTO {

    private Long id;
    private String name;
    private String licenseNumber;
    private String contactEmail;
    private String country;

    public static OperatorDTO convertToDTO(
            Operator operator) {

        return OperatorDTO.builder()
                .id(operator.getId())
                .name(operator.getName())
                .licenseNumber(operator.getLicenseNumber())
                .contactEmail(operator.getContactEmail())
                .country(operator.getCountry())
                .build();
    }

    public static List<OperatorDTO> convertToDTO(
            List<Operator> operators) {

        return operators.stream()
                .map(OperatorDTO::convertToDTO)
                .toList();
    }
}