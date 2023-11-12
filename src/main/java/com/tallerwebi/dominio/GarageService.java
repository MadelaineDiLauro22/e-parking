package com.tallerwebi.dominio;

import com.tallerwebi.model.Garage;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.presentacion.dto.OTPDTO;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import com.tallerwebi.model.Vehicle;

import javax.mail.MessagingException;
import java.util.List;

public interface GarageService {
    Garage getGarageByAdminUserId(Long garageAdminUserId);
    void registerVehicle(VehicleIngressDTO vehicleIngressDTO, OTPDTO otpDto, Long garageAdminUserId);
    void sendOtp(String email, Long id) throws MessagingException;
    void egressVehicle(String vehiclePatent, Long garageAdminUserId);
    List<Vehicle> getRegisteredVehicles(Long garageAdminUserId);
    MobileUser getUserByPatent(String patent);
    Vehicle getVehicleByPatent(String patent);
}
