package com.example.demo.controllers;

import com.example.demo.entities.Citizen;
import com.example.demo.services.CitizenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(
            CitizenService citizenService) {

        this.citizenService = citizenService;
    }

    @PostMapping("/add")
    public Citizen add(@RequestBody Citizen citizen) {

        return citizenService.add(citizen);
    }

    @GetMapping("/getAll")
    public List<Citizen> getAll() {

        return citizenService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Citizen getById(@PathVariable Long id) {

        return citizenService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Citizen update(
            @PathVariable Long id,
            @RequestBody Citizen citizen) {

        return citizenService.update(id, citizen);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        citizenService.delete(id);

        return "Citizen deleted successfully";
    }
}
