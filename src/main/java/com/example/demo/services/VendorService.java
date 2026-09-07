package com.example.demo.services;

import com.example.demo.entities.Vendor;
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

    public Vendor add(Vendor vendor) {

        vendor.setId(null);
        vendor.setIsActive(true);
        vendor.setCreatedDate(LocalDateTime.now());
        vendor.setUpdatedDate(LocalDateTime.now());

        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAll() {

        return vendorRepository.findAll()
                .stream()
                .filter(vendor ->
                        Boolean.TRUE.equals(
                                vendor.getIsActive()))
                .toList();
    }

    public Vendor getById(Long id) {

        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor not found"));

        if (!Boolean.TRUE.equals(vendor.getIsActive())) {
            throw new RuntimeException(
                    "Vendor not found");
        }

        return vendor;
    }

    public Vendor update(Long id, Vendor request) {

        Vendor vendor = getById(id);

        vendor.setName(request.getName());
        vendor.setContactEmail(
                request.getContactEmail());
        vendor.setPhoneNumber(
                request.getPhoneNumber());
        vendor.setCountry(request.getCountry());
        vendor.setUpdatedDate(LocalDateTime.now());

        return vendorRepository.save(vendor);
    }

    public void delete(Long id) {

        Vendor vendor = getById(id);

        vendor.setIsActive(false);
        vendor.setUpdatedDate(LocalDateTime.now());

        vendorRepository.save(vendor);
    }
}