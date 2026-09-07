package com.example.demo.controllers;

import com.example.demo.dtos.ComplaintDTO;
import com.example.demo.services.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(
            ComplaintService complaintService) {

        this.complaintService = complaintService;
    }

    @PostMapping("/add")
    public ComplaintDTO add(
            @Valid @RequestBody ComplaintDTO dto) {

        return complaintService.add(dto);
    }

    @GetMapping("/getAll")
    public List<ComplaintDTO> getAll() {
        return complaintService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ComplaintDTO getById(
            @PathVariable Long id) {

        return complaintService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ComplaintDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintDTO dto) {

        return complaintService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        complaintService.delete(id);

        return "Complaint deleted successfully";
    }
}