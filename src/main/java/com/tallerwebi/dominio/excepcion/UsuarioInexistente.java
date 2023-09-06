package com.tallerwebi.dominio.excepcion;

public class UsuarioInexistente extends RuntimeException{

    public UsuarioInexistente(){
        super("Usuario inexistente");
    }
}
