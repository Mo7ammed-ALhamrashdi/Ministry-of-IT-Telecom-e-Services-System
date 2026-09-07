package com.example.demo.services;

import com.example.demo.entities.Complaint;
import com.example.demo.repositories.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(
            ComplaintRepository complaintRepository) {

        this.complaintRepository =
                complaintRepository;
    }

    public Complaint add(Complaint complaint) {

        complaint.setId(null);
        complaint.setIsActive(true);
        complaint.setCreatedDate(LocalDateTime.now());
        complaint.setUpdatedDate(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAll() {

        return complaintRepository.findAll()
                .stream()
                .filter(complaint ->
                        Boolean.TRUE.equals(
                                complaint.getIsActive()))
                .toList();
    }

    public Complaint getById(Long id) {

        Complaint complaint =
                complaintRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Complaint not found"));

        if (!Boolean.TRUE.equals(
                complaint.getIsActive())) {

            throw new RuntimeException(
                    "Complaint not found");
        }

        return complaint;
    }

    public Complaint update(
            Long id,
            Complaint request) {

        Complaint complaint = getById(id);

        complaint.setSubject(request.getSubject());
        complaint.setDescription(
                request.getDescription());
        complaint.setStatus(request.getStatus());
        complaint.setFiledDate(
                request.getFiledDate());
        complaint.setCitizen(request.getCitizen());
        complaint.setOperator(request.getOperator());
        complaint.setOfficer(request.getOfficer());
        complaint.setUpdatedDate(
                LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    public void delete(Long id) {

        Complaint complaint = getById(id);

        complaint.setIsActive(false);
        complaint.setUpdatedDate(LocalDateTime.now());

        complaintRepository.save(complaint);
    }
}