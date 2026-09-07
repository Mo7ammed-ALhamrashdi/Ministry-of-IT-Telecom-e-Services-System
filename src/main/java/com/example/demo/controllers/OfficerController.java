package com.example.demo.controllers;

import com.example.demo.dtos.OfficerDTO;
import com.example.demo.services.OfficerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/officer")
// Provides REST endpoints for managing ministry officers.
public class OfficerController {

    // OfficerService owns officer creation, retrieval, update, and delete behavior.
    private final OfficerService officerService;

    public OfficerController(
            OfficerService officerService) {

        this.officerService = officerService;
    }

    // @RequestBody maps incoming JSON to OfficerDTO, and @Valid enforces DTO validation rules.
    @PostMapping("/add")
    public OfficerDTO add(
            @Valid @RequestBody OfficerDTO dto) {

        return officerService.add(dto);
    }

    // Lists officers by delegating the read operation to the service layer.
    @GetMapping("/getAll")
    public List<OfficerDTO> getAll() {
        return officerService.getAll();
    }

    @GetMapping("/getById/{id}")
    public OfficerDTO getById(
            @PathVariable Long id) {

        return officerService.getById(id);
    }

    // PUT updates the existing officer identified by the URL id.
    @PutMapping("/update/{id}")
    public OfficerDTO update(
            @PathVariable Long id,
            @Valid @RequestBody OfficerDTO dto) {

        return  officerService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        officerService.delete(id);

        return "Officer deleted successfully";
    }
}
