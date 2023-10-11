package com.tallerwebi.presentacion.dto;

public class VehicleRegisterDTO {

    private String patent;

    private String brand;

    private String model;

    private String color;

    public VehicleRegisterDTO(String patent, String brand, String model, String color) {
        this.patent = patent;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public VehicleRegisterDTO() {
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
}
