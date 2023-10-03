package com.tallerwebi.helpers;

import com.tallerwebi.infraestructura.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }



}
