package com.tallerwebi.dominio.excepcion;

public class ParkingNotFoundException extends RuntimeException{
    public ParkingNotFoundException() {
        super("Parking not found");
    }

}
