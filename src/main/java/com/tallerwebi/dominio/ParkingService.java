package com.tallerwebi.dominio;

import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import java.util.List;

public interface ParkingService {

    List<Vehicle> getUserCarsList(Long idUsuario);

    boolean registerParking(ParkingRegisterDTO parkingRegisterDTO);
}
