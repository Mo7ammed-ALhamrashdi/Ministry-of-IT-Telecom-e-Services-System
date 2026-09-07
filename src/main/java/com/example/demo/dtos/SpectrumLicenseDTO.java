package com.example.demo.dtos;

import com.example.demo.entities.SpectrumLicense;
import com.example.demo.enums.LicenseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class SpectrumLicenseDTO {

    private Long id;

    @NotBlank(message = "Band name is required")
    @Size(max = 100, message = "Band name must not exceed 100 characters")
    private String bandName;

    @NotNull(message = "Frequency is required")
    @Positive(message = "Frequency must be greater than zero")
    private Double frequencyMhz;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "License status is required")
    private LicenseStatus status;

    @NotNull(message = "Operator id is required")
    private Long operatorId;

    public static SpectrumLicenseDTO convertToDTO(SpectrumLicense license) {
        return SpectrumLicenseDTO.builder()
                .id(license.getId())
                .bandName(license.getBandName())
                .frequencyMhz(license.getFrequencyMhz())
                .issueDate(license.getIssueDate())
                .expiryDate(license.getExpiryDate())
                .status(license.getStatus())
                .operatorId(
                        license.getOperator() != null
                                ? license.getOperator().getId()
                                : null
                )
                .build();
    }

    public static List<SpectrumLicenseDTO> convertToDTO(List<SpectrumLicense> licenses) {
        return licenses.stream()
                .map(SpectrumLicenseDTO::convertToDTO)
                .toList();
    }
}