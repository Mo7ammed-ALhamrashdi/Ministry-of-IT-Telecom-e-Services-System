package com.example.demo.controllers;

import com.example.demo.dtos.SpectrumLicenseDTO;
import com.example.demo.services.SpectrumLicenseService;
import jakarta.validation.Valid;
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
    public SpectrumLicenseDTO add(
            @Valid @RequestBody SpectrumLicenseDTO dto) {

        return spectrumLicenseService.add(dto);
    }

    @GetMapping("/getAll")
    public List<SpectrumLicenseDTO> getAll() {

        return spectrumLicenseService.getAll();
    }

    @GetMapping("/getById/{id}")
    public SpectrumLicenseDTO getById(
            @PathVariable Long id) {

        return spectrumLicenseService.getById(id);
    }

    @GetMapping("/expiringSoon")
    public List<SpectrumLicenseDTO>
    getExpiringSoon() {

        return spectrumLicenseService
                .getExpiringWithin30Days();
    }

    @PutMapping("/update/{id}")
    public SpectrumLicenseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody SpectrumLicenseDTO dto) {

        return spectrumLicenseService.update(
                id,
                dto
        );
    }

    @DeleteMapping("/delete/{id}")
    public  String delete(
            @PathVariable Long id) {

        spectrumLicenseService.delete(id);

        return "Spectrum license deleted successfully";
    }
}