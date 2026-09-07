package com.example.demo.controllers;

import com.example.demo.dtos.MinistryDTO;
import com.example.demo.services.MinistryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ministry")
// Handles API requests for ministry records.
public class MinistryController {

    // MinistryService contains the ministry business operations used by the endpoints.
    private final MinistryService ministryService;

    // Constructor injection gives the controller its required service dependency.
    public MinistryController(MinistryService ministryService) {
        this.ministryService = ministryService;
    }

    // Creates a ministry from the validated DTO provided in the request body.
    @PostMapping("/add")
    public MinistryDTO add(
            @Valid @RequestBody MinistryDTO dto) {

        return ministryService.add(dto);
    }

    @GetMapping("/getAll")
    public List<MinistryDTO> getAll() {
        return ministryService.getAll();
    }

    // @PathVariable connects the {id} placeholder in the route to this parameter.
    @GetMapping("/getById/{id}")
    public MinistryDTO getById(
            @PathVariable Long id) {

        return ministryService.getById(id);
    }

    @PutMapping("/update/{id}")
    public MinistryDTO update(
            @PathVariable Long id,
            @Valid @RequestBody MinistryDTO dto) {

        return  ministryService.update(id, dto);
    }

    // The controller only triggers deletion; the service decides the persistence behavior.
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        ministryService.delete(id);

        return "Ministry deleted successfully";
    }
}
