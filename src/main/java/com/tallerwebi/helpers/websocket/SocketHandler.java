package com.tallerwebi.helpers.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tallerwebi.dominio.excepcion.WebsocketError;
import com.tallerwebi.helpers.Mapper;
import com.tallerwebi.helpers.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private final NotificationService notificationService;
    private final Mapper mapper;

    public SocketHandler(NotificationService notificationService, Mapper mapper) {
        this.notificationService = notificationService;
        this.mapper = mapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        Long userId = mapper.getMapper().readValue(
                message.getPayload(),
                Long.class
        );
        notificationService.setWebsocketSession(userId, session);
        notificationService.sendMessage(userId);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        throw new WebsocketError(throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        notificationService.removeWebsocketSession(webSocketSession.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }



}
