package com.example.demo.controllers;

import com.example.demo.entities.Ministry;
import com.example.demo.services.MinistryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ministry")
public class MinistryController {

    private final MinistryService ministryService;

    public MinistryController(MinistryService ministryService) {
        this.ministryService = ministryService;
    }

    @PostMapping("/add")
    public Ministry add(@RequestBody Ministry ministry) {
        return ministryService.add(ministry);
    }

    @GetMapping("/getAll")
    public List<Ministry> getAll() {
        return ministryService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Ministry getById(@PathVariable Long id) {
        return ministryService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Ministry update(
            @PathVariable Long id,
            @RequestBody Ministry ministry) {

        return ministryService.update(id, ministry);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        ministryService.delete(id);

        return "Ministry deleted successfully";
    }
}