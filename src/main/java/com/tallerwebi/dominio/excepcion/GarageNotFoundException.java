package com.tallerwebi.dominio.excepcion;

public class GarageNotFoundException extends RuntimeException {
    public GarageNotFoundException() {
        super("Can't find Garage");
    }
}
