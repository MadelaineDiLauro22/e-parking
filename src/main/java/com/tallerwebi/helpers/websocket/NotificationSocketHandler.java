package com.tallerwebi.helpers.websocket;

import com.tallerwebi.dominio.excepcion.CantSendMessageException;
import com.tallerwebi.helpers.Mapper;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.presentacion.dto.NotificationDTO;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public abstract class NotificationSocketHandler {

    private WebSocketSession webSocketSession = null;
    private Long userId = null;


    public abstract void sendMessage();


    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
