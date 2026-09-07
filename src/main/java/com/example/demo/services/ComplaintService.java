package com.example.demo.services;

import com.example.demo.dtos.ComplaintDTO;
import com.example.demo.entities.Citizen;
import com.example.demo.entities.Complaint;
import com.example.demo.entities.Officer;
import com.example.demo.entities.Operator;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CitizenRepository;
import com.example.demo.repositories.ComplaintRepository;
import com.example.demo.repositories.OfficerRepository;
import com.example.demo.repositories.OperatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CitizenRepository citizenRepository;
    private final OperatorRepository operatorRepository;
    private final OfficerRepository officerRepository;

    public ComplaintService(
            ComplaintRepository complaintRepository,
            CitizenRepository citizenRepository,
            OperatorRepository operatorRepository,
            OfficerRepository officerRepository) {

        this.complaintRepository = complaintRepository;
        this.citizenRepository = citizenRepository;
        this.operatorRepository = operatorRepository;
        this.officerRepository = officerRepository;
    }

    public ComplaintDTO add(ComplaintDTO dto) {

        Citizen citizen =
                findCitizenById(dto.getCitizenId());

        Operator operator =
                findOperatorById(dto.getOperatorId());

        Complaint complaint =
                new Complaint();

        complaint.setSubject(dto.getSubject());
        complaint.setDescription(dto.getDescription());
        complaint.setStatus(dto.getStatus());
        complaint.setFiledDate(dto.getFiledDate());
        complaint.setCitizen(citizen);
        complaint.setOperator(operator);

        if (dto.getOfficerId() != null) {
            complaint.setOfficer(
                    findOfficerById(dto.getOfficerId())
            );
        }

        complaint.setIsActive(true);
        complaint.setCreatedDate(LocalDateTime.now());
        complaint.setUpdatedDate(LocalDateTime.now());

        return ComplaintDTO.convertToDTO(
                complaintRepository.save(complaint)
        );
    }

    public List<ComplaintDTO> getAll() {

        List<Complaint> complaints =
                complaintRepository.findAll()
                        .stream()
                        .filter(complaint ->
                                Boolean.TRUE.equals(
                                        complaint.getIsActive()
                                )
                        )
                        .toList();

        return ComplaintDTO.convertToDTO(
                complaints
        );
    }

    public ComplaintDTO getById(Long id) {

        return ComplaintDTO.convertToDTO(
                findComplaintById(id)
        );
    }

    public ComplaintDTO update(
            Long id,
            ComplaintDTO dto) {

        Complaint complaint =
                findComplaintById(id);

        complaint.setSubject(dto.getSubject());
        complaint.setDescription(dto.getDescription());
        complaint.setStatus(dto.getStatus());
        complaint.setFiledDate(dto.getFiledDate());

        complaint.setCitizen(
                findCitizenById(dto.getCitizenId())
        );

        complaint.setOperator(
                findOperatorById(dto.getOperatorId())
        );

        if (dto.getOfficerId() != null) {
            complaint.setOfficer(
                    findOfficerById(dto.getOfficerId())
            );
        } else {
            complaint.setOfficer(null);
        }

        complaint.setUpdatedDate(LocalDateTime.now());

        return ComplaintDTO.convertToDTO(
                complaintRepository.save(complaint)
        );
    }

    public void delete(Long id) {

        Complaint complaint =
                findComplaintById(id);

        complaint.setIsActive(false);
        complaint.setUpdatedDate(LocalDateTime.now());

        complaintRepository.save(complaint);
    }

    private Complaint findComplaintById(Long id) {

        Complaint complaint =
                complaintRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                complaint.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Complaint not found with id: " + id
            );
        }

        return complaint;
    }

    private Citizen findCitizenById(Long id) {

        Citizen citizen =
                citizenRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Citizen not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(citizen.getIsActive())) {
            throw new ResourceNotFoundException(
                    "Citizen not found with id: " + id
            );
        }

        return citizen;
    }

    private Operator findOperatorById(Long id) {

        Operator operator =
                operatorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Operator not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(operator.getIsActive())) {
            throw new ResourceNotFoundException(
                    "Operator not found with id: " + id
            );
        }

        return operator;
    }

    private Officer findOfficerById(Long id) {

        Officer officer =
                officerRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Officer not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(officer.getIsActive())) {
            throw new ResourceNotFoundException(
                    "Officer not found with id: " + id
            );
        }

        return officer;
    }
}