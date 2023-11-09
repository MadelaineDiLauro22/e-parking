package com.tallerwebi.presentacion.dto;

public class VehicleIngressDTO {

    private String patent;
    private String brand;
    private String model;
    private String color;
    private String userName;
    private String userEmail;

    public VehicleIngressDTO(VehicleIngressDTO dto) {
        this.patent = dto.patent;
        this.brand = dto.brand;
        this.model = dto.model;
        this.color = dto.color;
        this.userName = dto.userName;
        this.userEmail = dto.userEmail;
    }

    public VehicleIngressDTO() { }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
