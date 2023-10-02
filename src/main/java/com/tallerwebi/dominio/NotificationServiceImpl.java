package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;

import java.util.List;

public class NotificationServiceImpl implements NotificationService{

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> findAllByUser(Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);

        if (user == null) throw new UserNotFoundException();

        List<Notification> notifications = notificationRepository.findAllByUser(user);

        //if(notifications.isEmpty()) throw NotificationsNotFound();

        return notifications;
    }

    @Override
    public List<Notification> findAllByUserAndNotRead(Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);

        if (user == null) throw new UserNotFoundException();

        List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

        //if(notifications.isEmpty()) throw NotificationsNotFound();

        return notifications;
    }
}
