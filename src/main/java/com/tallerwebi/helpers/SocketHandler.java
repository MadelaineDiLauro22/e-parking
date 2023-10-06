package com.tallerwebi.helpers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.excepcion.CantSendMessageException;
import com.tallerwebi.dominio.excepcion.WebsocketError;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.presentacion.dto.NotificationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private WebSocketSession webSocketSession = null;
    private Long userId = null;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public SocketHandler(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws JsonProcessingException {
        System.out.println("connected: " + webSocketSession);
        this.webSocketSession = webSocketSession;
        //notificationSocketHandler.sendMessage();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        this.userId = mapper.readValue(
                message.getPayload(),
                Long.class
        );
        System.out.println("userId: " + userId);
        sendMessage();
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        throw new WebsocketError(throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        //webSocketSession.close();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessage()  {
        try {
            NotificationDTO notifications = getNotifications();
            webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsBytes(notifications)));
        } catch (IOException e) {
            throw new CantSendMessageException(e.getMessage());
        }
    }

    private NotificationDTO getNotifications() {
        try {
            //Long userId = (Long) webSocketSession.getAttributes().get("userId");
            MobileUser user = userRepository.findUserById(userId);
            List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

            int size = notifications.size();

            return new NotificationDTO(size, size > 0 ? notifications.get(0).getMessage() : "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
