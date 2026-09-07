package com.example.demo.repositories;

import com.example.demo.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    @Query("""
            SELECT p
            FROM Project p
            WHERE p.budget > :budget
            AND p.isActive = true
            ORDER BY p.budget DESC
            """)
    List<Project> findProjectsAboveBudget(
            @Param("budget") BigDecimal budget
    );
}