package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.ParkingPlace;

import java.util.List;

public interface ParkingPlaceRepository {

    ParkingPlace findById(Long id);
    ParkingPlace findGarageByUser(MobileUser user);
    List<ParkingPlace> findAll();
    void save(ParkingPlace parkingPlace);
}
