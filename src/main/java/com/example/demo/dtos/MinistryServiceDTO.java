package com.example.demo.dtos;

import com.example.demo.entities.MinistryService;
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
    private String name;
    private String description;
    private BigDecimal fee;
    private Integer processingDays;
    private Long departmentId;

    public static MinistryServiceDTO convertToDTO(
            MinistryService service) {

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

    public static List<MinistryServiceDTO> convertToDTO(
            List<MinistryService> services) {

        return services.stream()
                .map(MinistryServiceDTO::convertToDTO)
                .toList();
    }
}