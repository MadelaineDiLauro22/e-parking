package com.tallerwebi.dominio.excepcion;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {
        super("Ya existe el usuario");
    }
}
