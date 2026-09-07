package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "officers")
public class  Officer extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String designation;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "officer")
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "officer")
    private List<Inspection> inspections = new ArrayList<>();

    @OneToMany(mappedBy = "officer")
    private List<Complaint> complaints = new ArrayList<>();
}