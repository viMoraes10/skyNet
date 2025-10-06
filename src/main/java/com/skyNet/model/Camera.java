package com.skyNet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "camera")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "active", nullable = false)
    private Boolean active;

    // Construtor padrão (obrigatório para JPA)
    public Camera() {}

    // Construtor de conveniência
    public Camera(String name, String description, String status, Boolean active) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
