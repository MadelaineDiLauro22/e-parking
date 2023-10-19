package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationRestServiceImpl implements NotificationRestService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationRestServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void seeNotifications(Long userId) throws UserNotFoundException {
        MobileUser user = userRepository.findUserById(userId);
        if(user == null) throw new UserNotFoundException();

        List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

        notifications.forEach(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}
