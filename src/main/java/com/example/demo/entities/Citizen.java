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
@Table(name = "citizens")
public class Citizen extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String nationalId;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 150)
    private String email;

    @OneToMany(mappedBy = "citizen")
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "citizen")
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "citizen")
    private List<DomainRegistration> domainRegistrations = new ArrayList<>();
}