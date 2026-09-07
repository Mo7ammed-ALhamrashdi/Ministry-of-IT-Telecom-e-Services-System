package com.example.demo.entities;

import com.example.demo.enums.ApplicationStatus;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "applications")
public class Application extends BaseClass {

    @Column(nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status;

    @Column(nullable = false, unique = true, length = 50)
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private MinistryService service;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private Officer officer;

    @OneToOne(mappedBy = "application")
    private Payment payment;
    @OneToMany(mappedBy = "application")
    private List<Document> documents = new ArrayList<>();
}