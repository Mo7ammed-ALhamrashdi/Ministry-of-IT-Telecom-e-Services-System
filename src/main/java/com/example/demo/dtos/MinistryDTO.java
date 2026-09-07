package com.example.demo.dtos;

import com.example.demo.entities.Ministry;
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

    private Long id;
    private String name;
    private String address;

    public static MinistryDTO convertToDTO(Ministry ministry) {

        return MinistryDTO.builder()
                .id(ministry.getId())
                .name(ministry.getName())
                .address(ministry.getAddress())
                .build();
    }

    public static List<MinistryDTO> convertToDTO(
            List<Ministry> ministries) {

        return ministries.stream()
                .map(MinistryDTO::convertToDTO)
                .toList();
    }
}