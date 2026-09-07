package com.example.demo.services;

import com.example.demo.dtos.ComplaintDTO;
import com.example.demo.entities.Citizen;
import com.example.demo.entities.Complaint;
import com.example.demo.entities.Officer;
import com.example.demo.entities.Operator;
import com.example.demo.enums.ComplaintStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CitizenRepository;
import com.example.demo.repositories.ComplaintRepository;
import com.example.demo.repositories.OfficerRepository;
import com.example.demo.repositories.OperatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  ComplaintService {

    // Manages complaint intake, assignment, resolution, and operator-specific complaint views.
    private final ComplaintRepository complaintRepository;
    private final CitizenRepository citizenRepository;
    private final OperatorRepository operatorRepository;
    private final OfficerRepository officerRepository;

    public ComplaintService(
            ComplaintRepository complaintRepository,
            CitizenRepository citizenRepository,
            OperatorRepository operatorRepository,
            OfficerRepository officerRepository) {

        // Constructor injection provides the repositories needed to validate complaint relationships.
        this.complaintRepository = complaintRepository;
        this.citizenRepository = citizenRepository;
        this.operatorRepository = operatorRepository;
        this.officerRepository = officerRepository;
    }


    // Complaint creation links an active citizen to an active operator before saving the case.
    public ComplaintDTO add(
            ComplaintDTO dto) {

        Citizen citizen =
                findCitizenById(
                        dto.getCitizenId()
                );

        Operator operator =
                findOperatorById(
                        dto.getOperatorId()
                );

        Complaint complaint =
                new Complaint();

        complaint.setSubject(
                dto.getSubject()
        );

        complaint.setDescription(
                dto.getDescription()
        );

        complaint.setStatus(
                dto.getStatus()
        );

        complaint.setFiledDate(
                dto.getFiledDate()
        );

        complaint.setCitizen(
                citizen
        );

        complaint.setOperator(
                operator
        );


        if (dto.getOfficerId() != null) {

            // Officer assignment is optional during intake, so complaints can be filed before routing.
            Officer officer =
                    findOfficerById(
                            dto.getOfficerId()
                    );

            complaint.setOfficer(
                    officer
            );
        }


        complaint.setIsActive(true);

        complaint.setCreatedDate(
                LocalDateTime.now()
        );

        complaint.setUpdatedDate(
                LocalDateTime.now()
        );


        Complaint savedComplaint =
                complaintRepository.save(
                        complaint
                );


        return ComplaintDTO.convertToDTO(
                savedComplaint
        );
    }


    public List<ComplaintDTO> getAll() {

        List<Complaint> complaints =
                complaintRepository
                        .findAll()
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


    public ComplaintDTO getById(
            Long id) {

        Complaint complaint =
                findComplaintById(id);


        return ComplaintDTO.convertToDTO(
                complaint
        );
    }


    public ComplaintDTO update(
            Long id,
            ComplaintDTO dto) {

        Complaint complaint =
                findComplaintById(id);


        Citizen citizen =
                findCitizenById(
                        dto.getCitizenId()
                );


        Operator operator =
                findOperatorById(
                        dto.getOperatorId()
                );


        complaint.setSubject(
                dto.getSubject()
        );

        complaint.setDescription(
                dto.getDescription()
        );

        complaint.setStatus(
                dto.getStatus()
        );

        complaint.setFiledDate(
                dto.getFiledDate()
        );

        complaint.setCitizen(
                citizen
        );

        complaint.setOperator(
                operator
        );


        if (dto.getOfficerId() != null) {

            Officer officer =
                    findOfficerById(
                            dto.getOfficerId()
                    );

            complaint.setOfficer(
                    officer
            );

        } else {

            complaint.setOfficer(null);
        }


        complaint.setUpdatedDate(
                LocalDateTime.now()
        );


        Complaint updatedComplaint =
                complaintRepository.save(
                        complaint
                );


        return ComplaintDTO.convertToDTO(
                updatedComplaint
        );
    }


    // Assigning an officer also moves the complaint into active processing.
    public ComplaintDTO assignOfficer(
            Long complaintId,
            Long officerId) {

        Complaint complaint =
                findComplaintById(
                        complaintId
                );


        Officer officer =
                findOfficerById(
                        officerId
                );


        complaint.setOfficer(
                officer
        );


        complaint.setStatus(
                ComplaintStatus.IN_PROGRESS
        );


        complaint.setUpdatedDate(
                LocalDateTime.now()
        );


        Complaint updatedComplaint =
                complaintRepository.save(
                        complaint
                );


        return ComplaintDTO.convertToDTO(
                updatedComplaint
        );
    }


    public ComplaintDTO resolve(
            Long complaintId) {

        Complaint complaint =
                findComplaintById(
                        complaintId
                );


        if (ComplaintStatus.RESOLVED
                .equals(
                        complaint.getStatus()
                )) {

            throw new IllegalArgumentException(
                    "Complaint is already resolved"
            );
        }


        complaint.setStatus(
                ComplaintStatus.RESOLVED
        );


        complaint.setUpdatedDate(
                LocalDateTime.now()
        );


        Complaint resolvedComplaint =
                complaintRepository.save(
                        complaint
                );


        return ComplaintDTO.convertToDTO(
                resolvedComplaint
        );
    }


    // This custom query returns operator complaints whose status is not RESOLVED.
    public List<ComplaintDTO>
    getOpenComplaintsByOperator(
            Long operatorId) {

        findOperatorById(
                operatorId
        );


        List<Complaint> complaints =
                complaintRepository
                        .findOpenComplaintsByOperator(
                                operatorId,
                                ComplaintStatus.RESOLVED
                        );


        return ComplaintDTO.convertToDTO(
                complaints
        );
    }


    public void delete(
            Long id) {

        Complaint complaint =
                findComplaintById(id);


        complaint.setIsActive(false);


        complaint.setUpdatedDate(
                LocalDateTime.now()
        );


        complaintRepository.save(
                complaint
        );
    }


    // ResourceNotFoundException is used for missing or inactive complaints to keep API behavior consistent.
    private Complaint findComplaintById(
            Long id) {

        Complaint complaint =
                complaintRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Complaint not found with id: "
                                                + id
                                )
                        );


        if (!Boolean.TRUE.equals(
                complaint.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Complaint not found with id: "
                            + id
            );
        }


        return complaint;
    }


    private Citizen findCitizenById(
            Long id) {

        Citizen citizen =
                citizenRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Citizen not found with id: "
                                                + id
                                )
                        );


        if (!Boolean.TRUE.equals(
                citizen.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Citizen not found with id: "
                            + id
            );
        }


        return citizen;
    }


    private Operator findOperatorById(
            Long id) {

        Operator operator =
                operatorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Operator not found with id: "
                                                + id
                                )
                        );


        if (!Boolean.TRUE.equals(
                operator.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Operator not found with id: "
                            + id
            );
        }


        return operator;
    }


    // Officer lookup protects assignments from using inactive officer records.
    private Officer findOfficerById(
            Long id) {

        Officer officer =
                officerRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Officer not found with id: "
                                                + id
                                )
                        );


        if (!Boolean.TRUE.equals(
                officer.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Officer not found with id: "
                            + id
            );
        }


        return officer;
    }
}
