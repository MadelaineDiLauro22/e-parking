package com.tallerwebi.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.model.Notification;
import org.springframework.web.socket.*;

import java.time.Instant;
import java.util.Date;


public class NotificationWebsocketHandler implements WebSocketHandler {

    private WebSocketSession session = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        session = webSocketSession;
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        webSocketSession.close();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessage() {

    }

}
