package com.tallerwebi.dominio.excepcion;

public class OTPNotFoundException extends RuntimeException{
    public OTPNotFoundException(String message) {
        super(message);
    }
}
