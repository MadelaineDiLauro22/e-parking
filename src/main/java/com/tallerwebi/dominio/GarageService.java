package com.tallerwebi.dominio;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import com.tallerwebi.model.Vehicle;
import java.util.List;

public interface GarageService {

    void registerVehicle(VehicleIngressDTO vehicleIngressDTO, Long garageAdminUserId);
    void egressVehicle(String vehiclePatent, Long garageAdminUserId);
    List<Vehicle> getRegisteredVehicles(Long garageAdminUserId);
    MobileUser getUserByPatent(String patent);
    Vehicle getVehicleByPatent(String patent);
}
