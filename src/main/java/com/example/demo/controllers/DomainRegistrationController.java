package com.example.demo.controllers;

import com.example.demo.entities.DomainRegistration;
import com.example.demo.services.DomainRegistrationService;
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
    public DomainRegistration add(
            @RequestBody DomainRegistration domain) {

        return service.add(domain);
    }

    @GetMapping("/getAll")
    public List<DomainRegistration> getAll() {

        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public DomainRegistration getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public DomainRegistration update(
            @PathVariable Long id,
            @RequestBody DomainRegistration domain) {

        return service.update(id, domain);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        service.delete(id);

        return "Domain Registration deleted successfully";
    }
}