package com.example.demo.controllers;

import com.example.demo.dtos.CitizenDTO;
import com.example.demo.services.CitizenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen")
// Exposes citizen management endpoints as JSON-based REST operations.
public class CitizenController {

    // The service performs citizen operations while the controller handles HTTP details.
    private final CitizenService citizenService;

    public CitizenController(
            CitizenService citizenService) {

        this.citizenService = citizenService;
    }

    // @RequestBody converts the incoming JSON into a CitizenDTO before validation.
    @PostMapping("/add")
    public CitizenDTO add(
            @Valid @RequestBody CitizenDTO dto) {

        return citizenService.add(dto);
    }

    // GET requests retrieve citizen data and do not require a request body.
    @GetMapping("/getAll")
    public List<CitizenDTO> getAll() {
        return citizenService.getAll();
    }

    @GetMapping("/getById/{id}")
    public CitizenDTO getById(
            @PathVariable Long id) {

        return citizenService.getById(id);
    }

    // Returning a DTO keeps the API response separate from the database entity model.
    @PutMapping("/update/{id}")
    public  CitizenDTO update(
            @PathVariable Long id,
            @Valid @RequestBody CitizenDTO dto) {

        return citizenService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        citizenService.delete(id);

        return "Citizen deleted successfully";
    }
}
