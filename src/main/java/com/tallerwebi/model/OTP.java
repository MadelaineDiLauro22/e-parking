package com.tallerwebi.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

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
    private Date expirationDate;

    public OTP(String otpKey, String userEmail, Long idGarage, int expirationTime) {
        this.otpKey = otpKey;
        this.userEmail = userEmail;
        this.idGarage = idGarage;
        this.expirationDate = calculateExpiration(expirationTime);
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

    private Date calculateExpiration(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationTime);

        Date date = calendar.getTime();

        return date;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}