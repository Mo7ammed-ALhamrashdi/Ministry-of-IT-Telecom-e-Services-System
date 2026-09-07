package com.example.demo.repositories;

import com.example.demo.entities.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository
        extends JpaRepository<Citizen, Long> {
}