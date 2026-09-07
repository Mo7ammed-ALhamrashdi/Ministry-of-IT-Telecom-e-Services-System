package com.example.demo.controllers;

import com.example.demo.dtos.OperatorDTO;
import com.example.demo.services.OperatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
// Handles REST requests for telecom operators.
public class OperatorController {

    // OperatorService is injected so operator business rules stay outside the controller.
    private final OperatorService operatorService;

    // The constructor is the single place where this controller receives its dependency.
    public OperatorController(
            OperatorService operatorService) {

        this.operatorService = operatorService;
    }

    // Creates an operator using validated DTO data from the request body.
    @PostMapping("/add")
    public OperatorDTO add(
            @Valid @RequestBody OperatorDTO dto) {

        return operatorService.add(dto);
    }

    @GetMapping("/getAll")
    public List<OperatorDTO> getAll() {
        return operatorService.getAll();
    }

    // Retrieves an operator selected by the id value embedded in the URL.
    @GetMapping("/getById/{id}")
    public OperatorDTO getById(
            @PathVariable Long id) {

        return operatorService.getById(id);
    }

    @PutMapping("/update/{id}")
    public OperatorDTO update(
            @PathVariable Long id,
            @Valid @RequestBody OperatorDTO dto) {

        return  operatorService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        operatorService.delete(id);

        return "Operator deleted successfully";
    }
}
