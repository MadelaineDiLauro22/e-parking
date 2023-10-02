package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Vehicle;

import java.util.List;

public class ProfileResponseDTO {

    List<Vehicle> vehicles;

    List<Parking> parkings;

    public ProfileResponseDTO(List<Vehicle> vehicles, List<Parking> parkings) {
        this.vehicles = vehicles;
        this.parkings = parkings;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Parking> getParkings() {
        return parkings;
    }

}
