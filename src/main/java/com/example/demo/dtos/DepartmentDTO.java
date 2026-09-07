package com.example.demo.dtos;

import com.example.demo.entities.Department;
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
    private String name;
    private String description;
    private Long ministryId;

    public static DepartmentDTO convertToDTO(
            Department department) {

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

    public static List<DepartmentDTO> convertToDTO(
            List<Department> departments) {

        return departments.stream()
                .map(DepartmentDTO::convertToDTO)
                .toList();
    }
}