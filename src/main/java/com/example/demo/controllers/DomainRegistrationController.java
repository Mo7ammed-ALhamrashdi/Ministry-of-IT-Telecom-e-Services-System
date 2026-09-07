package com.example.demo.controllers;

import com.example.demo.dtos.DomainRegistrationDTO;
import com.example.demo.services.DomainRegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domain")
public class DomainRegistrationController {

    private final DomainRegistrationService domainRegistrationService;

    public DomainRegistrationController(
            DomainRegistrationService domainRegistrationService) {

        this.domainRegistrationService =
                domainRegistrationService;
    }

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
    public DomainRegistrationDTO getById(
            @PathVariable Long id) {

        return domainRegistrationService.getById(id);
    }

    @PutMapping("/update/{id}")
    public DomainRegistrationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DomainRegistrationDTO dto) {

        return domainRegistrationService.update(
                id,
                dto
        );
    }

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