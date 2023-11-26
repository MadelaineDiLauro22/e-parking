package com.tallerwebi.helpers.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public abstract class NotificationSocketHandler {

    //private WebSocketSession webSocketSession = null;
    //private Long userId = null;

    private final Map<Long, WebSocketSession> sessions;

    public NotificationSocketHandler() {
        this.sessions = new HashMap<>();
    }

    public abstract void sendMessage(Long userId);


    /*public WebSocketSession getWebSocketSession() {
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
    }*/

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
