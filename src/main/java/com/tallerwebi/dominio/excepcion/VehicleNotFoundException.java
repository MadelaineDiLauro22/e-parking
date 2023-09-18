package com.tallerwebi.dominio.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(){
        super("Vehículo inexistente");
    }
}
