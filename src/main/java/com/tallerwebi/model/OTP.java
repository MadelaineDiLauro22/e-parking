package com.tallerwebi.model;

import org.springframework.beans.factory.annotation.Value;

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

    @Transient
    @Value("${otp.expiration.time}")
    private int expirationTime;

    public OTP(String otpKey, String userEmail, Long idGarage) {
        this.otpKey = otpKey;
        this.userEmail = userEmail;
        this.idGarage = idGarage;
        this.expirationDate = calculateExpiration();
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

    private Date calculateExpiration(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationTime);

        Date date = calendar.getTime();

        return date;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}