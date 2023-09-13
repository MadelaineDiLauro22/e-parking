package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import java.util.List;

public interface ParkingService {

    List<Vehicle> getUserCarsList(Long idUsuario) throws UserNotFoundException;

    boolean registerParking(ParkingRegisterDTO parkingRegisterDTO);
}
