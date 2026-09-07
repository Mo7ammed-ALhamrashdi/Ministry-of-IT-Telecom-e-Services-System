package com.example.demo.dtos;

import com.example.demo.entities.Vendor;
import jakarta.validation.constraints.Email;
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
public class  VendorDTO {

    private Long id;

    @NotBlank(message = "Vendor name is required")
    @Size(max = 100, message = "Vendor name must not exceed 100 characters")
    private String name;

    @Email(message = "Contact email must be valid")
    @Size(max = 150, message = "Contact email must not exceed 150 characters")
    private String contactEmail;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    public static VendorDTO convertToDTO(Vendor vendor) {
        return VendorDTO.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .contactEmail(vendor.getContactEmail())
                .phoneNumber(vendor.getPhoneNumber())
                .country(vendor.getCountry())
                .build();
    }

    public static List<VendorDTO> convertToDTO(List<Vendor> vendors) {
        return vendors.stream()
                .map(VendorDTO::convertToDTO)
                .toList();
    }
}