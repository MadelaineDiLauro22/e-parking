package com.tallerwebi.presentacion.dto;

public class OTPDTO {
    private String otpKey;

    public OTPDTO(String key) {
        this.otpKey = key;
    }

    public OTPDTO() {
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }

    public String getOtpKey() {
        return otpKey;
    }
}
