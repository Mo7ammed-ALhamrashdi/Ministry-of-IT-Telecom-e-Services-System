package com.example.demo.controllers;

import com.example.demo.dtos.VendorDTO;
import com.example.demo.services.VendorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(
            VendorService vendorService) {

        this.vendorService = vendorService;
    }

    @PostMapping("/add")
    public VendorDTO add(
            @Valid @RequestBody VendorDTO dto) {

        return vendorService.add(dto);
    }

    @GetMapping("/getAll")
    public List<VendorDTO> getAll() {
        return vendorService.getAll();
    }

    @GetMapping("/getById/{id}")
    public VendorDTO getById(
            @PathVariable Long id) {

        return vendorService.getById(id);
    }

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