package com.tallerwebi.presentacion.dto;

public class OTPDTO {
    private String otpKey;

    public OTPDTO(String key) {
        this.otpKey = key;
    }

    public String getOtpKey() {
        return otpKey;
    }
}
