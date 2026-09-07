package com.example.demo.controllers;

import com.example.demo.dtos.ApplicationDTO;
import com.example.demo.services.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PostMapping("/add")
    public ApplicationDTO add(
            @Valid @RequestBody ApplicationDTO dto) {

        return applicationService.add(dto);
    }

    @GetMapping("/getAll")
    public List<ApplicationDTO> getAll() {
        return applicationService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ApplicationDTO getById(
            @PathVariable Long id) {

        return applicationService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ApplicationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationDTO dto) {

        return applicationService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        applicationService.delete(id);

        return "Application deleted successfully";
    }
}