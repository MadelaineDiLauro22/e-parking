package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Usuario;
import com.tallerwebi.presentacion.DatosLogin;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(DatosLogin request) throws UsuarioExistente;

}
