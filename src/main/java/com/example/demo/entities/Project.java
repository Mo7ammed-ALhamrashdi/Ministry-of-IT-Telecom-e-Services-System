package com.example.demo.entities;

import com.example.demo.enums.ProjectStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseClass {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private BigDecimal budget;

    @Column(nullable = false)
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "ministry_id", nullable = false)
    private Ministry ministry;

    @OneToMany(mappedBy = "project")
    private List<Milestone> milestones = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "project_vendors",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id")
    )
    private List<Vendor> vendors = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Document> documents = new ArrayList<>();
}