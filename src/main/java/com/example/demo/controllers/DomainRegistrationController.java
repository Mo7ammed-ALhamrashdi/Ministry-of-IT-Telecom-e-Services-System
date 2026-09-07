package com.example.demo.controllers;

import com.example.demo.dtos.DomainRegistrationDTO;
import com.example.demo.services.DomainRegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domain")
public class DomainRegistrationController {

    private final DomainRegistrationService service;

    public DomainRegistrationController(
            DomainRegistrationService service) {

        this.service = service;
    }

    @PostMapping("/add")
    public DomainRegistrationDTO add(
            @Valid @RequestBody DomainRegistrationDTO dto) {

        return service.add(dto);
    }

    @GetMapping("/getAll")
    public List<DomainRegistrationDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public DomainRegistrationDTO getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public DomainRegistrationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DomainRegistrationDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        service.delete(id);

        return "Domain Registration deleted successfully";
    }
}