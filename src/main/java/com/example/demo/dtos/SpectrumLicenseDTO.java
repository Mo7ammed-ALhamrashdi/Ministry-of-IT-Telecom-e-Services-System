package com.example.demo.dtos;

import com.example.demo.entities.SpectrumLicense;
import com.example.demo.enums.LicenseStatus;
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
    private String bandName;
    private Double frequencyMhz;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private LicenseStatus status;
    private Long operatorId;

    public static SpectrumLicenseDTO convertToDTO(
            SpectrumLicense license) {

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

    public static List<SpectrumLicenseDTO> convertToDTO(
            List<SpectrumLicense> licenses) {

        return licenses.stream()
                .map(SpectrumLicenseDTO::convertToDTO)
                .toList();
    }
}