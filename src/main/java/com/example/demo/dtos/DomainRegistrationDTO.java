package com.example.demo.dtos;

import com.example.demo.entities.DomainRegistration;
import com.example.demo.enums.DomainStatus;
import jakarta.validation.constraints.NotBlank;
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
public class DomainRegistrationDTO {

    private Long id;

    @NotBlank(message = "Domain name is required")
    @Size(max = 150, message = "Domain name must not exceed 150 characters")
    private String domainName;

    @NotNull(message = "Registered date is required")
    private LocalDate registeredDate;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Domain status is required")
    private DomainStatus status;

    @NotNull(message = "Citizen id is required")
    private Long citizenId;

    public static DomainRegistrationDTO convertToDTO(DomainRegistration domain) {
        return DomainRegistrationDTO.builder()
                .id(domain.getId())
                .domainName(domain.getDomainName())
                .registeredDate(domain.getRegisteredDate())
                .expiryDate(domain.getExpiryDate())
                .status(domain.getStatus())
                .citizenId(
                        domain.getCitizen() != null
                                ? domain.getCitizen().getId()
                                : null
                )
                .build();
    }

    public static List<DomainRegistrationDTO> convertToDTO(List<DomainRegistration> domains) {
        return domains.stream()
                .map(DomainRegistrationDTO::convertToDTO)
                .toList();
    }
}