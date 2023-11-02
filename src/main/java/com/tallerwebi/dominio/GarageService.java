package com.tallerwebi.dominio;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import com.tallerwebi.model.Vehicle;
import java.util.List;

public interface GarageService {

    void registerVehicle(VehicleIngressDTO vehicleIngressDTO, Long userId);
    void egressVehicle(String vehiclePatent, Long userId);
    List<Vehicle> getRegisteredVehicles();
    MobileUser getUserByPatent(String patent);
    Vehicle getVehicleByPatent(String patent);
}
