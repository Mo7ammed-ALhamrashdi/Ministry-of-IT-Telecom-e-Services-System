package com.example.demo.controllers;

import com.example.demo.dtos.MinistryServiceDTO;
import com.example.demo.services.GovernmentServiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class GovernmentServiceController {

    private final GovernmentServiceService service;

    public GovernmentServiceController(
            GovernmentServiceService service) {

        this.service = service;
    }

    @PostMapping("/add")
    public MinistryServiceDTO add(
            @Valid @RequestBody MinistryServiceDTO dto) {

        return service.add(dto);
    }

    @GetMapping("/getAll")
    public List<MinistryServiceDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public MinistryServiceDTO getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public MinistryServiceDTO update(
            @PathVariable Long id,
            @Valid @RequestBody MinistryServiceDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        service.delete(id);

        return "Service deleted successfully";
    }
}