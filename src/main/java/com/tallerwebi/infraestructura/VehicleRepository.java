package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehiculo;

import java.util.List;

public interface VehicleRepository {

    List<Vehiculo> obtenerVehiculosPorUsuario(MobileUser user);

}
