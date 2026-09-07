package com.example.demo.controllers;

import com.example.demo.dtos.InspectionDTO;
import com.example.demo.services.InspectionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(
            InspectionService inspectionService) {

        this.inspectionService = inspectionService;
    }

    @PostMapping("/add")
    public InspectionDTO add(
            @Valid @RequestBody InspectionDTO dto) {

        return inspectionService.add(dto);
    }

    @GetMapping("/getAll")
    public List<InspectionDTO> getAll() {
        return inspectionService.getAll();
    }

    @GetMapping("/getById/{id}")
    public InspectionDTO getById(
            @PathVariable Long id) {

        return inspectionService.getById(id);
    }

    @PutMapping("/update/{id}")
    public InspectionDTO update(
            @PathVariable Long id,
            @Valid @RequestBody InspectionDTO dto) {

        return inspectionService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        inspectionService.delete(id);

        return "Inspection deleted successfully";
    }
}