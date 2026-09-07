package com.example.demo.controllers;

import com.example.demo.dtos.ComplaintDTO;
import com.example.demo.services.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
// Manages complaint intake, assignment, updates, resolution, and deletion requests.
public class ComplaintController {

    // ComplaintService owns the workflow rules for complaint processing.
    private final ComplaintService complaintService;

    // Spring uses this constructor to provide the service dependency when the controller is created.
    public ComplaintController(
            ComplaintService complaintService) {

        this.complaintService = complaintService;
    }


    // ADD COMPLAINT
    // Accepts a validated complaint DTO so invalid request data is rejected before service logic runs.
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
    // Uses two path variables to identify both the complaint and the officer being assigned.
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
    // The service handles the soft-delete behavior so the controller does not manage persistence flags.
    @DeleteMapping("/delete/{id}")
    public String  delete(
            @PathVariable Long id) {

        complaintService.delete(id);

        return "Complaint deleted successfully";
    }
}
