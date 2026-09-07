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


    // ADD COMPLAINT
    @PostMapping("/add")
    public ComplaintDTO add(
            @Valid @RequestBody ComplaintDTO dto) {

        return complaintService.add(dto);
    }


    // GET ALL COMPLAINTS
    @GetMapping("/getAll")
    public List<ComplaintDTO> getAll() {

        return complaintService.getAll();
    }


    // GET COMPLAINT BY ID
    @GetMapping("/getById/{id}")
    public ComplaintDTO getById(
            @PathVariable Long id) {

        return complaintService.getById(id);
    }


    // GET OPEN COMPLAINTS BY OPERATOR
    @GetMapping("/openByOperator/{operatorId}")
    public List<ComplaintDTO> getOpenComplaintsByOperator(
            @PathVariable Long operatorId) {

        return complaintService
                .getOpenComplaintsByOperator(operatorId);
    }


    // UPDATE COMPLAINT
    @PutMapping("/update/{id}")
    public ComplaintDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintDTO dto) {

        return complaintService.update(
                id,
                dto
        );
    }


    // ASSIGN OFFICER
    @PutMapping(
            "/assignOfficer/{complaintId}/{officerId}"
    )
    public ComplaintDTO assignOfficer(
            @PathVariable Long complaintId,
            @PathVariable Long officerId) {

        return complaintService.assignOfficer(
                complaintId,
                officerId
        );
    }


    // RESOLVE COMPLAINT
    @PutMapping("/resolve/{id}")
    public ComplaintDTO resolve(
            @PathVariable Long id) {

        return complaintService.resolve(id);
    }


    // SOFT DELETE
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        complaintService.delete(id);

        return "Complaint deleted successfully";
    }
}