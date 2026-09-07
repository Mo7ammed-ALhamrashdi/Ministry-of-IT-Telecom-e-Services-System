package com.example.demo.services;

import com.example.demo.entities.Inspection;
import com.example.demo.repositories.InspectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;

    public InspectionService(
            InspectionRepository inspectionRepository) {

        this.inspectionRepository =
                inspectionRepository;
    }

    public Inspection add(Inspection inspection) {

        inspection.setId(null);
        inspection.setIsActive(true);
        inspection.setCreatedDate(LocalDateTime.now());
        inspection.setUpdatedDate(LocalDateTime.now());

        return inspectionRepository.save(inspection);
    }

    public List<Inspection> getAll() {

        return inspectionRepository.findAll()
                .stream()
                .filter(inspection ->
                        Boolean.TRUE.equals(
                                inspection.getIsActive()))
                .toList();
    }

    public Inspection getById(Long id) {

        Inspection inspection =
                inspectionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Inspection not found"));

        if (!Boolean.TRUE.equals(
                inspection.getIsActive())) {

            throw new RuntimeException(
                    "Inspection not found");
        }

        return inspection;
    }

    public Inspection update(
            Long id,
            Inspection request) {

        Inspection inspection = getById(id);

        inspection.setInspectionDate(
                request.getInspectionDate());
        inspection.setResult(request.getResult());
        inspection.setNotes(request.getNotes());
        inspection.setOperator(request.getOperator());
        inspection.setOfficer(request.getOfficer());
        inspection.setUpdatedDate(
                LocalDateTime.now());

        return inspectionRepository.save(inspection);
    }

    public void delete(Long id) {

        Inspection inspection = getById(id);

        inspection.setIsActive(false);
        inspection.setUpdatedDate(
                LocalDateTime.now());

        inspectionRepository.save(inspection);
    }
}