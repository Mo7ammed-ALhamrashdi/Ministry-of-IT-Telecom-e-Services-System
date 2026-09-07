package com.example.demo.controllers;

import com.example.demo.entities.Officer;
import com.example.demo.services.OfficerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/officer")
public class OfficerController {

    private final OfficerService officerService;

    public OfficerController(
            OfficerService officerService) {

        this.officerService = officerService;
    }

    @PostMapping("/add")
    public Officer add(@RequestBody Officer officer) {

        return officerService.add(officer);
    }

    @GetMapping("/getAll")
    public List<Officer> getAll() {

        return officerService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Officer getById(@PathVariable Long id) {

        return officerService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Officer update(
            @PathVariable Long id,
            @RequestBody Officer officer) {

        return officerService.update(id, officer);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        officerService.delete(id);

        return "Officer deleted successfully";
    }
}