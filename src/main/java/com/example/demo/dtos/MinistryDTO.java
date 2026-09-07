package com.example.demo.dtos;

import com.example.demo.entities.Ministry;
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
public class MinistryDTO {

    private  Long id;

    @NotBlank(message = "Ministry name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    public static MinistryDTO convertToDTO(Ministry ministry) {
        return MinistryDTO.builder()
                .id(ministry.getId())
                .name(ministry.getName())
                .address(ministry.getAddress())
                .build();
    }

    public static List<MinistryDTO> convertToDTO(List<Ministry> ministries) {
        return ministries.stream()
                .map(MinistryDTO::convertToDTO)
                .toList();
    }
}