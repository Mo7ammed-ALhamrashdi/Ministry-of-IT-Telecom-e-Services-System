package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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

}