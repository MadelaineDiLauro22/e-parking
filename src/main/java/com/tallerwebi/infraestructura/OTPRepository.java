package com.tallerwebi.infraestructura;

import com.tallerwebi.model.OTP;
import org.hibernate.Criteria;

public interface OTPRepository {
    void save(OTP otp);
    boolean exists(String userEmail, Long idGarage, String key);
}
