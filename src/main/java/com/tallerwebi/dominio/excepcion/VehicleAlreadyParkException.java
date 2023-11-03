package com.tallerwebi.dominio.excepcion;

public class VehicleAlreadyParkException extends RuntimeException {
    public VehicleAlreadyParkException(){
        super("La patente ya existe en el garaje.");
    }
}
