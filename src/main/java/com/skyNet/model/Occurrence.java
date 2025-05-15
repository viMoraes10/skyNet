package com.skyNet.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "occurrence")
public class Occurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double reliable;

    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getReliable() {
        return reliable;
    }

    public void setReliable(Double reliable) {
        this.reliable = reliable;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Occurrence() {
    }
}
