package com.tallerwebi.helpers.websocket;

import org.springframework.web.socket.WebSocketSession;

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
