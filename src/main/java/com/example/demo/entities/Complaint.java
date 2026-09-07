package com.example.demo.entities;

import com.example.demo.enums.ComplaintStatus;

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
@Table(name = "complaints")
public class Complaint extends BaseClass {

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ComplaintStatus status;

    @Column(nullable = false)
    private LocalDate filedDate;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private Officer officer;
}