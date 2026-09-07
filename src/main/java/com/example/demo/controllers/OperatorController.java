package com.example.demo.controllers;

import com.example.demo.entities.Operator;
import com.example.demo.services.OperatorService;
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
    public Operator add(@RequestBody Operator operator) {

        return operatorService.add(operator);
    }

    @GetMapping("/getAll")
    public List<Operator> getAll() {

        return operatorService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Operator getById(@PathVariable Long id) {

        return operatorService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Operator update(
            @PathVariable Long id,
            @RequestBody Operator operator) {

        return operatorService.update(id, operator);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        operatorService.delete(id);

        return "Operator deleted successfully";
    }
}