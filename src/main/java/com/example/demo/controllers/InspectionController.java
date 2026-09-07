package com.example.demo.controllers;

import com.example.demo.entities.Inspection;
import com.example.demo.services.InspectionService;
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
    public Inspection add(
            @RequestBody Inspection inspection) {

        return inspectionService.add(inspection);
    }

    @GetMapping("/getAll")
    public List<Inspection> getAll() {

        return inspectionService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Inspection getById(
            @PathVariable Long id) {

        return inspectionService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Inspection update(
            @PathVariable Long id,
            @RequestBody Inspection inspection) {

        return inspectionService.update(id, inspection);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        inspectionService.delete(id);

        return "Inspection deleted successfully";
    }
}