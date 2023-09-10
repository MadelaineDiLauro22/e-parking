package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.User;

public interface UserRepository {

    User findUserByMailAndPassword(String email, String password);
    void save(MobileUser usuario);
    User findUserByMail(String email);
    void modifyUser(User user);
    User findUserById(Long id);
}

