package com.example.demo.dtos;

import com.example.demo.entities.DomainRegistration;
import com.example.demo.enums.DomainStatus;
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
public class DomainRegistrationDTO {

    private Long id;
    private String domainName;
    private LocalDate registeredDate;
    private LocalDate expiryDate;
    private DomainStatus status;
    private Long citizenId;

    public static DomainRegistrationDTO convertToDTO(
            DomainRegistration domain) {

        return DomainRegistrationDTO.builder()
                .id(domain.getId())
                .domainName(domain.getDomainName())
                .registeredDate(
                        domain.getRegisteredDate())
                .expiryDate(domain.getExpiryDate())
                .status(domain.getStatus())
                .citizenId(
                        domain.getCitizen() != null
                                ? domain.getCitizen().getId()
                                : null
                )
                .build();
    }

    public static List<DomainRegistrationDTO> convertToDTO(
            List<DomainRegistration> domains) {

        return domains.stream()
                .map(DomainRegistrationDTO::convertToDTO)
                .toList();
    }
}