package com.example.demo.dtos;

import com.example.demo.entities.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    private Long id;
    private String name;
    private String contactEmail;
    private String phoneNumber;
    private String country;

    public static VendorDTO convertToDTO(
            Vendor vendor) {

        return VendorDTO.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .contactEmail(vendor.getContactEmail())
                .phoneNumber(vendor.getPhoneNumber())
                .country(vendor.getCountry())
                .build();
    }

    public static List<VendorDTO> convertToDTO(
            List<Vendor> vendors) {

        return vendors.stream()
                .map(VendorDTO::convertToDTO)
                .toList();
    }
}