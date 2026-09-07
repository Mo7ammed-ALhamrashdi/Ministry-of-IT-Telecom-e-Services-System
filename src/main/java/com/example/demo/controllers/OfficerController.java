package com.example.demo.controllers;

import com.example.demo.dtos.OfficerDTO;
import com.example.demo.services.OfficerService;
import jakarta.validation.Valid;
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
    public OfficerDTO add(
            @Valid @RequestBody OfficerDTO dto) {

        return officerService.add(dto);
    }

    @GetMapping("/getAll")
    public List<OfficerDTO> getAll() {
        return officerService.getAll();
    }

    @GetMapping("/getById/{id}")
    public OfficerDTO getById(
            @PathVariable Long id) {

        return officerService.getById(id);
    }

    @PutMapping("/update/{id}")
    public OfficerDTO update(
            @PathVariable Long id,
            @Valid @RequestBody OfficerDTO dto) {

        return officerService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        officerService.delete(id);

        return "Officer deleted successfully";
    }
}