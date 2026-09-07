package com.example.demo.repositories;

import com.example.demo.entities.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository
        extends JpaRepository<Milestone, Long> {
}