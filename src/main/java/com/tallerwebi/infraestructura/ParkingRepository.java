package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Parking;

import java.util.List;

public interface ParkingRepository {

    void save(Parking parking);
    Parking findById(Long id);
    List<Parking> findParkingsByUser(MobileUser user);

}
