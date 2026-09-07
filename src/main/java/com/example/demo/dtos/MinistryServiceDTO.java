package com.example.demo.dtos;

import com.example.demo.entities.MinistryService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinistryServiceDTO {

    private Long id;

    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Fee is required")
    @PositiveOrZero(message = "Fee cannot be negative")
    private BigDecimal fee;

    @NotNull(message = "Processing days is required")
    @Positive(message = "Processing days must be greater than zero")
    private Integer processingDays;

    @NotNull(message = "Department id is required")
    private Long departmentId;

    public static MinistryServiceDTO convertToDTO(MinistryService service) {
        return MinistryServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .fee(service.getFee())
                .processingDays(service.getProcessingDays())
                .departmentId(
                        service.getDepartment() != null
                                ? service.getDepartment().getId()
                                : null
                )
                .build();
    }

    public static List<MinistryServiceDTO> convertToDTO(List<MinistryService> services) {
        return services.stream()
                .map(MinistryServiceDTO::convertToDTO)
                .toList();
    }
}