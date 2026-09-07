package com.example.demo.controllers;

import com.example.demo.dtos.MinistryServiceDTO;
import com.example.demo.services.GovernmentServiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
// Publishes endpoints for ministry service definitions offered to citizens.
public class GovernmentServiceController {

    // The injected service keeps service-catalog rules outside the controller.
    private final GovernmentServiceService service;

    // Spring resolves this constructor parameter from the application context.
    public GovernmentServiceController(
            GovernmentServiceService service) {

        this.service = service;
    }

    // @Valid checks the MinistryServiceDTO constraints before the service creates the record.
    @PostMapping("/add")
    public MinistryServiceDTO add(
            @Valid @RequestBody MinistryServiceDTO dto) {

        return service.add(dto);
    }

    // Returns DTOs so clients see the API model instead of internal entity objects.
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
    public  MinistryServiceDTO update(
            @PathVariable Long id,
            @Valid @RequestBody MinistryServiceDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        service.delete(id);

        // A simple message is returned after the service completes the delete operation.
        return "Service deleted successfully";
    }
}
