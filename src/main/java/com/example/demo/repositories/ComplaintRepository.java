package com.example.demo.repositories;

import com.example.demo.entities.Complaint;
import com.example.demo.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

    @Query("""
            SELECT c
            FROM Complaint c
            WHERE c.operator.id = :operatorId
            AND c.status <> :resolvedStatus
            AND c.isActive = true
            ORDER BY c.filedDate DESC
            """)
    List<Complaint> findOpenComplaintsByOperator(
            @Param("operatorId") Long operatorId,
            @Param("resolvedStatus") ComplaintStatus resolvedStatus
    );
}