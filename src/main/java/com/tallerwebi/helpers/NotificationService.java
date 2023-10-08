package com.tallerwebi.helpers;

import com.tallerwebi.dominio.excepcion.CantSendMessageException;
import com.tallerwebi.helpers.websocket.NotificationSocketHandler;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.presentacion.dto.NotificationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
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
            NotificationDTO notifications = getNotifications();
            super.getWebSocketSession().sendMessage(new TextMessage(mapper.getMapper().writeValueAsBytes(notifications)));
        } catch (IOException e) {
            throw new CantSendMessageException(e.getMessage());
        }
    }

    private NotificationDTO getNotifications() {
        try {
            //Long userId = (Long) webSocketSession.getAttributes().get("userId");
            MobileUser user = userRepository.findUserById(super.getUserId());
            List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

            int size = notifications.size();

            return new NotificationDTO(size, size > 0 ? notifications.get(0).getMessage() : "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
