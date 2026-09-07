package com.example.demo.repositories;

import com.example.demo.entities.Ministry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  MinistryRepository
        extends JpaRepository<Ministry, Long> {
}