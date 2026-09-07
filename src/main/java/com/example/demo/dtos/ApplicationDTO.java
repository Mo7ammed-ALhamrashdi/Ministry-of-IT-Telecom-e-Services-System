package com.example.demo.dtos;

import com.example.demo.entities.Application;
import com.example.demo.enums.ApplicationStatus;
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
public class ApplicationDTO {

    private Long id;

    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    private String referenceNumber;

    @NotNull(message = "Citizen id is required")
    private Long citizenId;

    @NotNull(message = "Service id is required")
    private Long serviceId;

    private Long officerId;

    private Long paymentId;

    public static ApplicationDTO convertToDTO(Application application) {
        return ApplicationDTO.builder()
                .id(application.getId())
                .applicationDate(application.getApplicationDate())
                .status(application.getStatus())
                .referenceNumber(application.getReferenceNumber())
                .citizenId(
                        application.getCitizen() != null
                                ? application.getCitizen().getId()
                                : null
                )
                .serviceId(
                        application.getService() != null
                                ? application.getService().getId()
                                : null
                )
                .officerId(
                        application.getOfficer() != null
                                ? application.getOfficer().getId()
                                : null
                )
                .paymentId(
                        application.getPayment() != null
                                ? application.getPayment().getId()
                                : null
                )
                .build();
    }

    public static List<ApplicationDTO> convertToDTO(List<Application> applications) {
        return applications.stream()
                .map(ApplicationDTO::convertToDTO)
                .toList();
    }
}