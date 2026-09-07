package com.example.demo.controllers;

import com.example.demo.dtos.MilestoneDTO;
import com.example.demo.services.MilestoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/milestone")
public class MilestoneController {

    private final MilestoneService milestoneService;

    public MilestoneController(
            MilestoneService milestoneService) {

        this.milestoneService = milestoneService;
    }

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

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        milestoneService.delete(id);

        return "Milestone deleted successfully";
    }
}