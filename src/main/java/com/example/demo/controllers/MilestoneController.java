package com.example.demo.controllers;

import com.example.demo.entities.Milestone;
import com.example.demo.services.MilestoneService;
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
    public Milestone add(
            @RequestBody Milestone milestone) {

        return milestoneService.add(milestone);
    }

    @GetMapping("/getAll")
    public List<Milestone> getAll() {

        return milestoneService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Milestone getById(
            @PathVariable Long id) {

        return milestoneService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Milestone update(
            @PathVariable Long id,
            @RequestBody Milestone milestone) {

        return milestoneService.update(id, milestone);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        milestoneService.delete(id);

        return "Milestone deleted successfully";
    }
}