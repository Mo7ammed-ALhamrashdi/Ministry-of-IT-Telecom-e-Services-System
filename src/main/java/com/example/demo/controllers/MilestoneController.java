package com.example.demo.controllers;

import com.example.demo.dtos.MilestoneDTO;
import com.example.demo.services.MilestoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/milestone")
// Exposes project milestone endpoints for creation, lookup, update, and deletion.
public class MilestoneController {

    // MilestoneService coordinates milestone operations for this web controller.
    private final MilestoneService milestoneService;

    // Constructor injection makes the controller easier to test with a mocked service.
    public MilestoneController(
            MilestoneService milestoneService) {

        this.milestoneService = milestoneService;
    }

    // Receives a validated milestone DTO and passes it to the service for creation.
    @PostMapping("/add")
    public MilestoneDTO add(
            @Valid @RequestBody MilestoneDTO dto) {

        return milestoneService.add(dto);
    }

    @GetMapping("/getAll")
    public List<MilestoneDTO> getAll() {
        return milestoneService.getAll();
    }

    @GetMapping("/getById/{id}")
    public MilestoneDTO getById(
            @PathVariable Long id) {

        return milestoneService.getById(id);
    }

    @PutMapping("/update/{id}")
    public  MilestoneDTO update(
            @PathVariable Long id,
            @Valid @RequestBody MilestoneDTO dto) {

        return milestoneService.update(id, dto);
    }

    // Delegates removal to the service instead of directly accessing a repository.
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        milestoneService.delete(id);

        return "Milestone deleted successfully";
    }
}
