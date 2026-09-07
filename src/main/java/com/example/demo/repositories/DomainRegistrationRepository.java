package com.example.demo.repositories;

import com.example.demo.entities.DomainRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRegistrationRepository
        extends JpaRepository<DomainRegistration, Long> {
}
