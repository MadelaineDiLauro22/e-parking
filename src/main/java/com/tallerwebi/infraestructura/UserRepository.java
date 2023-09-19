package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.User;

public interface UserRepository {

    MobileUser findUserByMailAndPassword(String email, String password);
    void save(MobileUser usuario);
    MobileUser findUserByMail(String email);
    MobileUser findUserById(Long id);
}

