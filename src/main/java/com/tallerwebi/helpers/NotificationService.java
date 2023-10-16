package com.tallerwebi.helpers;

import com.tallerwebi.dominio.excepcion.CantSendMessageException;
import com.tallerwebi.dominio.excepcion.NotificationServiceException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.helpers.websocket.NotificationSocketHandler;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.presentacion.dto.NotificationDTO;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService extends NotificationSocketHandler {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, Mapper mapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void sendMessage()  {
        try {
            if(super.getWebSocketSession() == null) throw new NotificationServiceException("Websocket session not connected");
            NotificationDTO notifications = getNotifications();
            super.getWebSocketSession().sendMessage(new TextMessage(mapper.getMapper().writeValueAsBytes(notifications)));
        } catch (IOException e) {
            throw new CantSendMessageException(e.getMessage());
        }
    }

    @Transactional
    public void registerNotification(NotificationRequestDTO request) {
        try {
            MobileUser user = userRepository.findUserById(super.getUserId());

            if(user == null) throw new UserNotFoundException();

            Notification notification = new Notification(request.getTitle(), request.getMessage(), Date.from(Instant.now()), user);
            user.addNotification(notification);

            userRepository.save(user);
            notificationRepository.save(notification);
        } catch (Exception e) {
            throw new NotificationServiceException("Can't register notification. Exception: " + e.getMessage());
        }
    }

    @Transactional
    public void registerAndSendNotification(NotificationRequestDTO request) {
        registerNotification(request);
        if (super.getWebSocketSession().isOpen()) sendMessage();
    }

    private NotificationDTO getNotifications() {
        try {
            MobileUser user = userRepository.findUserById(super.getUserId());
            List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

            int size = notifications.size();

            return new NotificationDTO(size, size > 0 ? notifications.get(0).getMessage() : "");
        } catch (Exception e) {
            throw new NotificationServiceException("Can't get notifications. Exception: " + e.getMessage());
        }
    }
}
