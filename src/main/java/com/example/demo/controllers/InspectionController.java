package com.example.demo.controllers;

import com.example.demo.dtos.InspectionDTO;
import com.example.demo.services.InspectionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inspection")
// Handles REST endpoints for inspection records and their lifecycle.
public class InspectionController {

    // InspectionService performs the actual inspection data operations.
    private final InspectionService inspectionService;

    public InspectionController(
            InspectionService inspectionService) {

        this.inspectionService = inspectionService;
    }

    // The POST endpoint accepts an InspectionDTO from JSON and sends it to the service.
    @PostMapping("/add")
    public InspectionDTO add(
            @Valid @RequestBody InspectionDTO dto) {

        return inspectionService.add(dto);
    }

    @GetMapping("/getAll")
    public List<InspectionDTO> getAll() {
        return inspectionService.getAll();
    }

    // The id path variable identifies which inspection record should be returned.
    @GetMapping("/getById/{id}")
    public InspectionDTO getById(
            @PathVariable Long id) {

        return inspectionService.getById(id);
    }

    @PutMapping("/update/{id}")
    public  InspectionDTO update(
            @PathVariable Long id,
            @Valid @RequestBody InspectionDTO dto) {

        return inspectionService.update(id, dto);
    }

    // The service handles the delete operation and any inspection-specific rules.
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        inspectionService.delete(id);

        return "Inspection deleted successfully";
    }
}
