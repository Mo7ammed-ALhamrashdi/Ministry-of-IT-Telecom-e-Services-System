package com.example.demo.repositories;

import com.example.demo.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  VendorRepository
        extends JpaRepository<Vendor, Long> {
}