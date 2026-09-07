package com.example.demo.controllers;

import com.example.demo.entities.Application;
import com.example.demo.services.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PostMapping("/add")
    public Application add(
            @RequestBody Application application) {

        return applicationService.add(application);
    }

    @GetMapping("/getAll")
    public List<Application> getAll() {

        return applicationService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Application getById(
            @PathVariable Long id) {

        return applicationService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Application update(
            @PathVariable Long id,
            @RequestBody Application application) {

        return applicationService.update(id, application);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        applicationService.delete(id);

        return "Application deleted successfully";
    }
}
