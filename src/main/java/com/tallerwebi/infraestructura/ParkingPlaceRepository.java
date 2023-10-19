package com.tallerwebi.infraestructura;

import com.tallerwebi.model.ParkingPlace;

import java.util.List;

public interface ParkingPlaceRepository {

    ParkingPlace findById(Long id);
    List<ParkingPlace> findAll();
    void save(ParkingPlace parkingPlace);
}
