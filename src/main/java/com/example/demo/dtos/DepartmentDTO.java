package com.example.demo.dtos;

import com.example.demo.entities.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DepartmentDTO {

    private Long id;

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Ministry id is required")
    private Long ministryId;

    public static DepartmentDTO convertToDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .ministryId(
                        department.getMinistry() != null
                                ? department.getMinistry().getId()
                                : null
                )
                .build();
    }

    public static List<DepartmentDTO> convertToDTO(List<Department> departments) {
        return departments.stream()
                .map(DepartmentDTO::convertToDTO)
                .toList();
    }
}