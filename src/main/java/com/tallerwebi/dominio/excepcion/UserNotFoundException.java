package com.tallerwebi.dominio.excepcion;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("Usuario inexistente");
    }
}
