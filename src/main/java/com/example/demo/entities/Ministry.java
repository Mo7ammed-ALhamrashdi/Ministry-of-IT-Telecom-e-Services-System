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
@Table(name = "ministries")
public class Ministry extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String address;

    @OneToMany(mappedBy = "ministry")
    private List<Department> departments = new ArrayList<>();

    @OneToMany(mappedBy = "ministry")
    private List<Project> projects = new ArrayList<>();
}