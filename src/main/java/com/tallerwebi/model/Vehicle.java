package com.tallerwebi.model;

import javax.persistence.*;
import java.util.Objects;

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

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }


    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    public MobileUser getUser() {
        return user;
    }

    public void setUser(MobileUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(patent, vehicle.patent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patent);
    }

    @Override
    public String toString(){
        return model + " - " + patent;
    }
}
