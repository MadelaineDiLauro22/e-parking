package com.tallerwebi.dominio.excepcion;

public class VehicleExistInGarageException extends RuntimeException{
    public VehicleExistInGarageException() {
        super("El vehiculo esta registrado en el garage.");
    }
}
