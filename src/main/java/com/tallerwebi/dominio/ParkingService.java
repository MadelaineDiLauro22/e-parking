package com.tallerwebi.dominio;

import com.tallerwebi.model.Vehiculo;

import java.util.List;

public interface ParkingService {

    List<Vehiculo> getUserCarsList(Long idUsuario);

}
