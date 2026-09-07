package com.example.demo.controllers;

import com.example.demo.dtos.CitizenDTO;
import com.example.demo.services.CitizenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(
            CitizenService citizenService) {

        this.citizenService = citizenService;
    }

    @PostMapping("/add")
    public CitizenDTO add(
            @Valid @RequestBody CitizenDTO dto) {

        return citizenService.add(dto);
    }

    @GetMapping("/getAll")
    public List<CitizenDTO> getAll() {
        return citizenService.getAll();
    }

    @GetMapping("/getById/{id}")
    public CitizenDTO getById(
            @PathVariable Long id) {

        return citizenService.getById(id);
    }

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