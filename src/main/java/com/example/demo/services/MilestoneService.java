package com.example.demo.services;

import com.example.demo.entities.Milestone;
import com.example.demo.repositories.MilestoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneService(
            MilestoneRepository milestoneRepository) {

        this.milestoneRepository =
                milestoneRepository;
    }

    public Milestone add(Milestone milestone) {

        milestone.setId(null);
        milestone.setIsActive(true);
        milestone.setCreatedDate(LocalDateTime.now());
        milestone.setUpdatedDate(LocalDateTime.now());

        return milestoneRepository.save(milestone);
    }

    public List<Milestone> getAll() {

        return milestoneRepository.findAll()
                .stream()
                .filter(milestone ->
                        Boolean.TRUE.equals(
                                milestone.getIsActive()))
                .toList();
    }

    public Milestone getById(Long id) {

        Milestone milestone =
                milestoneRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Milestone not found"));

        if (!Boolean.TRUE.equals(
                milestone.getIsActive())) {

            throw new RuntimeException(
                    "Milestone not found");
        }

        return milestone;
    }

    public Milestone update(
            Long id,
            Milestone request) {

        Milestone milestone = getById(id);

        milestone.setTitle(request.getTitle());
        milestone.setDueDate(request.getDueDate());
        milestone.setStatus(request.getStatus());
        milestone.setProject(request.getProject());
        milestone.setUpdatedDate(
                LocalDateTime.now());

        return milestoneRepository.save(milestone);
    }

    public void delete(Long id) {

        Milestone milestone = getById(id);

        milestone.setIsActive(false);
        milestone.setUpdatedDate(LocalDateTime.now());

        milestoneRepository.save(milestone);
    }
}
