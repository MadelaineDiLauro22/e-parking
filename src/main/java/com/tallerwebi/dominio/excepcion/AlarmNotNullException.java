package com.tallerwebi.dominio.excepcion;

public class AlarmNotNullException extends RuntimeException {
    public AlarmNotNullException() {
        super("Alarm date must not be null");
    }
}
