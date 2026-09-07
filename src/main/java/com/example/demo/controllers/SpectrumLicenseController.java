package com.example.demo.controllers;

import com.example.demo.entities.SpectrumLicense;
import com.example.demo.services.SpectrumLicenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spectrumLicense")
public class SpectrumLicenseController {

    private final SpectrumLicenseService spectrumLicenseService;

    public SpectrumLicenseController(
            SpectrumLicenseService spectrumLicenseService) {

        this.spectrumLicenseService =
                spectrumLicenseService;
    }

    @PostMapping("/add")
    public SpectrumLicense add(
            @RequestBody SpectrumLicense spectrumLicense) {

        return spectrumLicenseService.add(
                spectrumLicense);
    }

    @GetMapping("/getAll")
    public List<SpectrumLicense> getAll() {

        return spectrumLicenseService.getAll();
    }

    @GetMapping("/getById/{id}")
    public SpectrumLicense getById(
            @PathVariable Long id) {

        return spectrumLicenseService.getById(id);
    }

    @PutMapping("/update/{id}")
    public SpectrumLicense update(
            @PathVariable Long id,
            @RequestBody SpectrumLicense spectrumLicense) {

        return spectrumLicenseService.update(
                id,
                spectrumLicense);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        spectrumLicenseService.delete(id);

        return "Spectrum License deleted successfully";
    }
}