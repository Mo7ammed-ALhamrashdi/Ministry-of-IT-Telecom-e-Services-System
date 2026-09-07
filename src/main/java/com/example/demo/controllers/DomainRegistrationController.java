package com.example.demo.controllers;

import com.example.demo.dtos.DomainRegistrationDTO;
import com.example.demo.services.DomainRegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domain")
// Handles domain registration requests, updates, renewals, and deletion calls.
public class DomainRegistrationController {

    // The domain registration service contains validation and renewal behavior.
    private final DomainRegistrationService domainRegistrationService;

    // Constructor injection supplies the service that this controller delegates to.
    public DomainRegistrationController(
            DomainRegistrationService domainRegistrationService) {

        this.domainRegistrationService =
                domainRegistrationService;
    }

    // Creates a domain registration from validated request JSON.
    @PostMapping("/add")
    public DomainRegistrationDTO add(
            @Valid @RequestBody DomainRegistrationDTO dto) {

        return domainRegistrationService.add(dto);
    }

    @GetMapping("/getAll")
    public List<DomainRegistrationDTO> getAll() {

        return domainRegistrationService.getAll();
    }

    @GetMapping("/getById/{id}")
    public  DomainRegistrationDTO getById(
            @PathVariable Long id) {

        return domainRegistrationService.getById(id);
    }

    // PUT update combines the URL id with the DTO body to modify an existing registration.
    @PutMapping("/update/{id}")
    public DomainRegistrationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DomainRegistrationDTO dto) {

        return domainRegistrationService.update(
                id,
                dto
        );
    }

    // Renewing a domain is a business action that updates an existing registration through the service.
    @PutMapping("/renew/{id}")
    public DomainRegistrationDTO renew(
            @PathVariable Long id,
            @Valid @RequestBody DomainRegistrationDTO dto) {

        return domainRegistrationService.renew(
                id,
                dto
        );
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        domainRegistrationService.delete(id);

        return "Domain registration deleted successfully";
    }
}
