package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "operators")
public class Operator extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @Column(length = 150)
    private String contactEmail;

    @Column(length = 100)
    private String country;

    @OneToMany(mappedBy = "operator")
    private List<SpectrumLicense> spectrumLicenses = new ArrayList<>();

    @OneToMany(mappedBy = "operator")
    private List<Inspection> inspections = new ArrayList<>();

    @OneToMany(mappedBy = "operator")
    private List<Complaint> complaints = new ArrayList<>();
}