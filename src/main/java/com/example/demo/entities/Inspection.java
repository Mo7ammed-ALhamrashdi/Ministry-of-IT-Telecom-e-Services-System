package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inspections")
public class Inspection extends BaseClass {

    @Column(nullable = false)
    private LocalDate inspectionDate;

    @Column(length = 200)
    private String result;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "officer_id", nullable = false)
    private Officer officer;
}