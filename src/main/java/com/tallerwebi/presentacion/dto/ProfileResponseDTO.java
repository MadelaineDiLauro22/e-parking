package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Vehicle;

import java.util.List;
import java.util.Set;

public class ProfileResponseDTO {

    Set<Vehicle> vehicles;
    List<Parking> parkings;

    public ProfileResponseDTO(Set<Vehicle> vehicles, List<Parking> parkings) {
        this.vehicles = vehicles;
        this.parkings = parkings;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Parking> getParkings() {
        return parkings;
    }

}
