package com.example.demo.repositories;

import com.example.demo.entities.SpectrumLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface  SpectrumLicenseRepository
        extends JpaRepository<SpectrumLicense, Long> {

    @Query("""
            SELECT s
            FROM SpectrumLicense s
            WHERE s.isActive = true
            AND s.expiryDate BETWEEN :today AND :endDate
            ORDER BY s.expiryDate ASC
            """)
    List<SpectrumLicense> findExpiringLicenses(
            @Param("today") LocalDate today,
            @Param("endDate") LocalDate endDate
    );
}