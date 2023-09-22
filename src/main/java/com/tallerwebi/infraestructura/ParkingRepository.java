package com.tallerwebi.infraestructura;

import com.tallerwebi.model.Parking;

public interface ParkingRepository {

    void save(Parking parking);
    Parking findById(Long id);

}
