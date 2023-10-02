package com.tallerwebi.dominio;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;

import java.util.List;

public interface NotificationService {
     List<Notification> findAllByUser(Long idUser);
     List<Notification> findAllByUserAndNotRead(Long idUser);
}
