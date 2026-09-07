package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vendors")
public class Vendor extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 150)
    private String contactEmail;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String country;

    @ManyToMany(mappedBy = "vendors")
    private List<Project> projects = new ArrayList<>();
}