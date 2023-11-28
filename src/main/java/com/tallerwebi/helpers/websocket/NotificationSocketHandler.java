package com.tallerwebi.helpers.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public abstract class NotificationSocketHandler {


    private final Map<Long, WebSocketSession> sessions;

    public NotificationSocketHandler() {
        this.sessions = new HashMap<>();
    }

    public abstract void sendMessage(Long userId);


    public void setWebsocketSession(Long userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public WebSocketSession getWebsocketSession(Long userId) {
        return sessions.get(userId);
    }

    public void removeWebsocketSession(String sessionId) {
        sessions.values().removeIf(session -> session.getId().equals(sessionId));
    }

    public boolean isConnected(Long userId) {
        return sessions.containsKey(userId) && sessions.get(userId).isOpen();
    }
}
