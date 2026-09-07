package com.example.demo.repositories;

import com.example.demo.entities.Application;
import com.example.demo.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    @Query("""
            SELECT a
            FROM Application a
            WHERE a.status = :status
            AND a.isActive = true
            """)
    List<Application> findApplicationsByStatus(
            @Param("status") ApplicationStatus status
    );


    @Query("""
            SELECT a
            FROM Application a
            WHERE a.citizen.id = :citizenId
            AND a.isActive = true
            ORDER BY a.applicationDate DESC
            """)
    List<Application> findCitizenApplicationHistory(
            @Param("citizenId") Long citizenId
    );
}