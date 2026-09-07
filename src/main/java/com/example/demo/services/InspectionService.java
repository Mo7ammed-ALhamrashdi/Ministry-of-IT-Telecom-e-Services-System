package com.example.demo.services;

import com.example.demo.dtos.InspectionDTO;
import com.example.demo.entities.Inspection;
import com.example.demo.entities.Officer;
import com.example.demo.entities.Operator;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.InspectionRepository;
import com.example.demo.repositories.OfficerRepository;
import com.example.demo.repositories.OperatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  InspectionService {

    // Records operator inspections performed by officers and keeps those links active.
    private final InspectionRepository inspectionRepository;
    private final OperatorRepository operatorRepository;
    private final OfficerRepository officerRepository;

    public InspectionService(
            InspectionRepository inspectionRepository,
            OperatorRepository operatorRepository,
            OfficerRepository officerRepository) {

        this.inspectionRepository =
                inspectionRepository;

        this.operatorRepository =
                operatorRepository;

        this.officerRepository =
                officerRepository;
    }

    public InspectionDTO add(
            InspectionDTO dto) {

        Operator operator =
                findOperatorById(
                        dto.getOperatorId()
                );

        Officer officer =
                findOfficerById(
                        dto.getOfficerId()
                );

        // Inspection details come from the DTO while relationships are resolved through helpers.
        Inspection inspection =
                new Inspection();

        inspection.setInspectionDate(
                dto.getInspectionDate()
        );

        inspection.setResult(
                dto.getResult()
        );

        inspection.setNotes(
                dto.getNotes()
        );

        inspection.setOperator(
                operator
        );

        inspection.setOfficer(
                officer
        );

        inspection.setIsActive(true);

        inspection.setCreatedDate(
                LocalDateTime.now()
        );

        inspection.setUpdatedDate(
                LocalDateTime.now()
        );

        Inspection savedInspection =
                inspectionRepository.save(
                        inspection
                );

        return InspectionDTO.convertToDTO(
                savedInspection
        );
    }

    public List<InspectionDTO> getAll() {

        List<Inspection> inspections =
                inspectionRepository
                        .findAll()
                        .stream()
                        // Service reads expose only inspections that have not been soft deleted.
                        .filter(inspection ->
                                Boolean.TRUE.equals(
                                        inspection.getIsActive()
                                )
                        )
                        .toList();

        return InspectionDTO.convertToDTO(
                inspections
        );
    }

    public InspectionDTO getById(
            Long id) {

        return InspectionDTO.convertToDTO(
                findInspectionById(id)
        );
    }

    public InspectionDTO update(
            Long id,
            InspectionDTO dto) {

        Inspection inspection =
                findInspectionById(id);

        Operator operator =
                findOperatorById(
                        dto.getOperatorId()
                );

        Officer officer =
                findOfficerById(
                        dto.getOfficerId()
                );

        // Updating refreshes both inspection content and the linked active operator/officer pair.
        inspection.setInspectionDate(
                dto.getInspectionDate()
        );

        inspection.setResult(
                dto.getResult()
        );

        inspection.setNotes(
                dto.getNotes()
        );

        inspection.setOperator(
                operator
        );

        inspection.setOfficer(
                officer
        );

        inspection.setUpdatedDate(
                LocalDateTime.now()
        );

        Inspection updatedInspection =
                inspectionRepository.save(
                        inspection
                );

        return InspectionDTO.convertToDTO(
                updatedInspection
        );
    }

    public void delete(
            Long id) {

        Inspection inspection =
                findInspectionById(id);

        // Soft deletion preserves the inspection record for audit history.
        inspection.setIsActive(false);

        inspection.setUpdatedDate(
                LocalDateTime.now()
        );

        inspectionRepository.save(
                inspection
        );
    }

    private Inspection findInspectionById(
            Long id) {

        Inspection inspection =
                inspectionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inspection not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                inspection.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Inspection not found with id: "
                            + id
            );
        }

        return inspection;
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
