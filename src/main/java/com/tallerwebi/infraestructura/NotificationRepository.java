package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;

import java.util.List;

public interface NotificationRepository {

    List<Notification> findAllByUser(MobileUser user);
    List<Notification> findAllByUserAndNotRead(MobileUser user);
    void save(Notification notification);
}
