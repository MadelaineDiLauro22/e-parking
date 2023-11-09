package com.tallerwebi.model;

import javax.persistence.*;

@Entity
@Table(name = "OTP")
public class OTP {
    @Id
    @Column(name = "otp_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String otpKey;
    private String userEmail;
    private Long idGarage;

    public OTP(String otpKey, String userEmail, Long idGarage) {
        this.otpKey = otpKey;
        this.userEmail = userEmail;
        this.idGarage = idGarage;
    }

    public OTP() {
    }

    public Long getId() {
        return id;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getIdGarage() {
        return idGarage;
    }
}
