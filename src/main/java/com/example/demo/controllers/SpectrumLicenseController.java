package com.example.demo.controllers;

import com.example.demo.dtos.SpectrumLicenseDTO;
import com.example.demo.services.SpectrumLicenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spectrumLicense")
// Handles spectrum license endpoints for telecom licensing workflows.
public class SpectrumLicenseController {

    // SpectrumLicenseService centralizes license rules and data access coordination.
    private final SpectrumLicenseService spectrumLicenseService;

    // Spring injects the service through this constructor when building the controller bean.
    public SpectrumLicenseController(
            SpectrumLicenseService spectrumLicenseService) {

        this.spectrumLicenseService =
                spectrumLicenseService;
    }

    // Creates a license from validated request data before returning the saved DTO.
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

    // This business query returns licenses that are close to expiration.
    @GetMapping("/expiringSoon")
    public List<SpectrumLicenseDTO>
    getExpiringSoon() {

        return spectrumLicenseService
                .getExpiringWithin30Days();
    }

    // PUT updates an existing license using both the URL id and the request DTO.
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
