package com.tallerwebi.model;

import javax.persistence.*;

@Entity
@Table(name = "VEHICLE")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String patent;

    private String brand;

    private String model;

    private String color;

    private boolean isActive;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MobileUser user;

    public Vehicle(String patent, String brand, String model, String color) {
        this.patent = patent;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.isActive = true;
    }

    public Vehicle() {

    }

    public Long getId() {
        return id;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String marca) {
        this.brand = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }
}
