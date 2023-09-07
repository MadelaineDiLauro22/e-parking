package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Usuario;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(MobileUser usuario) throws UsuarioExistente;

}
