package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.model.ParkingPlace;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import java.util.List;

public interface ParkingService {

    List<Vehicle> getUserCarsList(Long idUsuario) throws UserNotFoundException, VehicleNotFoundException;
    List<ParkingPlace> getParkingPlaces();

    void registerParking(ParkingRegisterDTO parkingRegisterDTO, Long idUser) throws UserNotFoundException, VehicleNotFoundException;
}
