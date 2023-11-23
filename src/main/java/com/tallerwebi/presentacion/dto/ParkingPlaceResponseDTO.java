package com.tallerwebi.presentacion.dto;
import com.tallerwebi.model.Geolocation;

import java.util.Set;


public class ParkingPlaceResponseDTO {
    private String type;
    private Long id;
    private String name;
    private Geolocation geolocation;
    private String address;
    private float feePerHour;
    private float feeFraction;
    private long fractionTime;
    private Set<String> patents;
    private int numberOfCars;
    private Long userId;

    public ParkingPlaceResponseDTO(String type, Long id, String name, Geolocation geolocation, String address, float feePerHour, float feeFraction, long fractionTime) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.geolocation = geolocation;
        this.address = address;
        this.feePerHour = feePerHour;
        this.feeFraction = feeFraction;
        this.fractionTime = fractionTime;
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

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public float getFeePerHour() {
        return feePerHour;
    }

    public void setFeePerHour(float feePerHour) {
        this.feePerHour = feePerHour;
    }

    public float getFeeFraction() {
        return feeFraction;
    }

    public void setFeeFraction(float feeFraction) {
        this.feeFraction = feeFraction;
    }

    public long getFractionTime() {
        return fractionTime;
    }

    public void setFractionTime(long fractionTime) {
        this.fractionTime = fractionTime;
    }

    public Set<String> getPatents() {
        return patents;
    }

    public void setPatents(Set<String> patents) {
        this.patents = patents;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }
}
