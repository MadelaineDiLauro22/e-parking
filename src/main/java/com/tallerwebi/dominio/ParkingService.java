package com.tallerwebi.dominio;

import com.tallerwebi.model.Vehicle;

import java.util.List;

public interface ParkingService {

    List<Vehicle> getUserCarsList(Long idUsuario);

}
