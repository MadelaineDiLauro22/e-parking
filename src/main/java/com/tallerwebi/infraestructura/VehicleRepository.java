package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;

import java.util.List;

public interface VehicleRepository {

    List<Vehicle> findVehiclesByUser(MobileUser user);

    Vehicle findVehicleByPatent(String vehicle);
}
