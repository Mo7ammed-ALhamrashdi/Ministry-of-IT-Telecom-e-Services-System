package com.example.demo.entities;

import com.example.demo.enums.LicenseStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "spectrum_licenses")
public class SpectrumLicense extends BaseClass {

    @Column(nullable = false, length = 100)
    private String bandName;

    @Column(nullable = false)
    private Double frequencyMhz;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private LicenseStatus status;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;
}