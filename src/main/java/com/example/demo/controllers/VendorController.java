package com.example.demo.controllers;

import com.example.demo.dtos.VendorDTO;
import com.example.demo.services.VendorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
// Provides vendor management endpoints for project and procurement workflows.
public class VendorController {

    // VendorService handles vendor business operations for this controller.
    private final VendorService vendorService;

    public VendorController(
            VendorService vendorService) {

        this.vendorService = vendorService;
    }

    // Creates a vendor after Spring validates the incoming VendorDTO.
    @PostMapping("/add")
    public VendorDTO add(
            @Valid @RequestBody VendorDTO dto) {

        return vendorService.add(dto);
    }

    // Returns the full vendor list through the service layer.
    @GetMapping("/getAll")
    public List<VendorDTO> getAll() {
        return vendorService.getAll();
    }

    @GetMapping("/getById/{id}")
    public VendorDTO getById(
            @PathVariable Long id) {

        return vendorService.getById(id);
    }

    // Uses the path id plus DTO body to update an existing vendor.
    @PutMapping("/update/{id}")
    public VendorDTO update(
            @PathVariable Long id,
            @Valid @RequestBody VendorDTO dto) {

        return  vendorService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        vendorService.delete(id);

        return "Vendor deleted successfully";
    }
}
