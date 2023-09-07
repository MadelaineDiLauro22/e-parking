package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.model.Usuario;
import com.tallerwebi.presentacion.dto.DatosLogin;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(DatosLogin request) throws UsuarioExistente;

}
