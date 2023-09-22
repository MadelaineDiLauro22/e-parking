package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.User;
import com.tallerwebi.presentacion.dto.LoginDataDTO;

public interface LoginService {

    User searchUser(String email, String password);
    void registerUser(LoginDataDTO request) throws UserNotFoundException;

}
