package com.example.demo.controllers;

import com.example.demo.dtos.MinistryDTO;
import com.example.demo.services.MinistryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ministry")
public class MinistryController {

    private final MinistryService ministryService;

    public MinistryController(MinistryService ministryService) {
        this.ministryService = ministryService;
    }

    @PostMapping("/add")
    public MinistryDTO add(
            @Valid @RequestBody MinistryDTO dto) {

        return ministryService.add(dto);
    }

    @GetMapping("/getAll")
    public List<MinistryDTO> getAll() {
        return ministryService.getAll();
    }

    @GetMapping("/getById/{id}")
    public MinistryDTO getById(
            @PathVariable Long id) {

        return ministryService.getById(id);
    }

    @PutMapping("/update/{id}")
    public MinistryDTO update(
            @PathVariable Long id,
            @Valid @RequestBody MinistryDTO dto) {

        return ministryService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        ministryService.delete(id);

        return "Ministry deleted successfully";
    }
}