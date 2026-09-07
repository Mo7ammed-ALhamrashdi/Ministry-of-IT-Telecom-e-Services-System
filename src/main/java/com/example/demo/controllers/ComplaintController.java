package com.example.demo.controllers;

import com.example.demo.entities.Complaint;
import com.example.demo.services.ComplaintService;
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
    public Complaint add(
            @RequestBody Complaint complaint) {

        return complaintService.add(complaint);
    }

    @GetMapping("/getAll")
    public List<Complaint> getAll() {

        return complaintService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Complaint getById(
            @PathVariable Long id) {

        return complaintService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Complaint update(
            @PathVariable Long id,
            @RequestBody Complaint complaint) {

        return complaintService.update(id, complaint);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        complaintService.delete(id);

        return "Complaint deleted successfully";
    }
}
