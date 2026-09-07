package com.example.demo.repositories;

import com.example.demo.entities.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  OfficerRepository
        extends JpaRepository<Officer, Long> {

    @Query("""
            SELECT o
            FROM Officer o
            WHERE o.department.id = :departmentId
            AND o.isActive = true
            ORDER BY o.id ASC
            """)
    List<Officer> findActiveOfficersByDepartment(
            @Param("departmentId") Long departmentId
    );
}