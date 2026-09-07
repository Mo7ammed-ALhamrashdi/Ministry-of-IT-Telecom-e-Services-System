package com.example.demo.controllers;

import com.example.demo.entities.MinistryService;
import com.example.demo.services.GovernmentServiceService;
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
    public MinistryService add(
            @RequestBody MinistryService ministryService) {

        return service.add(ministryService);
    }

    @GetMapping("/getAll")
    public List<MinistryService> getAll() {

        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public MinistryService getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public MinistryService update(
            @PathVariable Long id,
            @RequestBody MinistryService ministryService) {

        return service.update(id, ministryService);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        service.delete(id);

        return "Service deleted successfully";
    }
}