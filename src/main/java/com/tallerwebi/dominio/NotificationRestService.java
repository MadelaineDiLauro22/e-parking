package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;

public interface NotificationRestService {

    void seeNotifications(Long userId) throws UserNotFoundException;

}
