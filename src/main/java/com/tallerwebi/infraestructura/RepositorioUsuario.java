package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Usuario;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(MobileUser usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
}

