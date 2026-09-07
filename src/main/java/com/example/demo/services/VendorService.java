package com.example.demo.services;

import com.example.demo.dtos.VendorDTO;
import com.example.demo.entities.Vendor;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(
            VendorRepository vendorRepository) {

        this.vendorRepository = vendorRepository;
    }

    public VendorDTO add(VendorDTO dto) {

        Vendor vendor = new Vendor();

        vendor.setName(dto.getName());
        vendor.setContactEmail(dto.getContactEmail());
        vendor.setPhoneNumber(dto.getPhoneNumber());
        vendor.setCountry(dto.getCountry());
        vendor.setIsActive(true);
        vendor.setCreatedDate(LocalDateTime.now());
        vendor.setUpdatedDate(LocalDateTime.now());

        return VendorDTO.convertToDTO(
                vendorRepository.save(vendor)
        );
    }

    public List<VendorDTO> getAll() {

        List<Vendor> vendors =
                vendorRepository.findAll()
                        .stream()
                        .filter(vendor ->
                                Boolean.TRUE.equals(
                                        vendor.getIsActive()
                                )
                        )
                        .toList();

        return VendorDTO.convertToDTO(vendors);
    }

    public VendorDTO getById(Long id) {

        return VendorDTO.convertToDTO(
                findVendorById(id)
        );
    }

    public VendorDTO update(
            Long id,
            VendorDTO dto) {

        Vendor vendor =
                findVendorById(id);

        vendor.setName(dto.getName());
        vendor.setContactEmail(dto.getContactEmail());
        vendor.setPhoneNumber(dto.getPhoneNumber());
        vendor.setCountry(dto.getCountry());
        vendor.setUpdatedDate(LocalDateTime.now());

        return VendorDTO.convertToDTO(
                vendorRepository.save(vendor)
        );
    }

    public void delete(Long id) {

        Vendor vendor =
                findVendorById(id);

        vendor.setIsActive(false);
        vendor.setUpdatedDate(LocalDateTime.now());

        vendorRepository.save(vendor);
    }

    private Vendor findVendorById(Long id) {

        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vendor not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                vendor.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Vendor not found with id: " + id
            );
        }

        return vendor;
    }
}