package com.example.demo.controllers;

import com.example.demo.dtos.OperatorDTO;
import com.example.demo.services.OperatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(
            OperatorService operatorService) {

        this.operatorService = operatorService;
    }

    @PostMapping("/add")
    public OperatorDTO add(
            @Valid @RequestBody OperatorDTO dto) {

        return operatorService.add(dto);
    }

    @GetMapping("/getAll")
    public List<OperatorDTO> getAll() {
        return operatorService.getAll();
    }

    @GetMapping("/getById/{id}")
    public OperatorDTO getById(
            @PathVariable Long id) {

        return operatorService.getById(id);
    }

    @PutMapping("/update/{id}")
    public OperatorDTO update(
            @PathVariable Long id,
            @Valid @RequestBody OperatorDTO dto) {

        return operatorService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        operatorService.delete(id);

        return "Operator deleted successfully";
    }
}