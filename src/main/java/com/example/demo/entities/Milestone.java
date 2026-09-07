package com.example.demo.entities;

import com.example.demo.enums.MilestoneStatus;

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
@Table(name = "milestones")
public class  Milestone extends BaseClass {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MilestoneStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}