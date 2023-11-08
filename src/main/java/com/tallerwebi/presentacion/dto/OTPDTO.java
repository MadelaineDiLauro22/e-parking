package com.tallerwebi.presentacion.dto;

import org.springframework.web.bind.annotation.RequestParam;

public class OTPDTO {
    private String otpKey;
    private String patent;
    private String brand;
    private String model;
    private String color;
    private String userName;
    private String userEmail;

    public OTPDTO(String key) {
        this.otpKey = key;
    }

    public OTPDTO(String patent,
                  String brand,
                  String model,
                  String color,
                  String userName,
                  String userEmail) {
        this.patent = patent;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public OTPDTO() {
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }

    public String getOtpKey() {
        return otpKey;
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
