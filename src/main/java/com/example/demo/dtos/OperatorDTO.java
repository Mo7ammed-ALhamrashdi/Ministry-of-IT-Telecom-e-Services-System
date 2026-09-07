package com.example.demo.dtos;

import com.example.demo.entities.Operator;
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
public class OperatorDTO {

    private  Long id;

    @NotBlank(message = "Operator name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "License number is required")
    @Size(max = 50, message = "License number must not exceed 50 characters")
    private String licenseNumber;

    @Email(message = "Contact email must be valid")
    @Size(max = 150, message = "Contact email must not exceed 150 characters")
    private String contactEmail;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    public static OperatorDTO convertToDTO(Operator operator) {
        return OperatorDTO.builder()
                .id(operator.getId())
                .name(operator.getName())
                .licenseNumber(operator.getLicenseNumber())
                .contactEmail(operator.getContactEmail())
                .country(operator.getCountry())
                .build();
    }

    public static List<OperatorDTO> convertToDTO(List<Operator> operators) {
        return operators.stream()
                .map(OperatorDTO::convertToDTO)
                .toList();
    }
}