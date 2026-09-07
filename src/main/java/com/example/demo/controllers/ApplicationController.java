package com.example.demo.controllers;

import com.example.demo.dtos.ApplicationDTO;
import com.example.demo.enums.ApplicationStatus;
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

        this.applicationService =
                applicationService;
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

        return applicationService
                .getById(id);
    }

    @GetMapping("/status/{status}")
    public List<ApplicationDTO> getByStatus(
            @PathVariable ApplicationStatus status) {

        return applicationService
                .getByStatus(status);
    }

    @GetMapping("/citizenHistory/{citizenId}")
    public List<ApplicationDTO> getCitizenHistory(
            @PathVariable Long citizenId) {

        return applicationService
                .getCitizenHistory(citizenId);
    }

    @PutMapping("/approve/{id}")
    public ApplicationDTO approve(
            @PathVariable Long id) {

        return applicationService
                .approve(id);
    }

    @PutMapping("/reject/{id}")
    public ApplicationDTO reject(
            @PathVariable Long id) {

        return applicationService
                .reject(id);
    }

    @PutMapping("/update/{id}")
    public ApplicationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationDTO dto) {

        return  applicationService.update(
                id,
                dto
        );
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        applicationService.delete(id);

        return "Application deleted successfully";
    }
}