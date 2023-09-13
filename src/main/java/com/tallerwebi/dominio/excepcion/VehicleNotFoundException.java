package com.tallerwebi.dominio.excepcion;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(){
        super("Vehículo inexistente");
    }
}
