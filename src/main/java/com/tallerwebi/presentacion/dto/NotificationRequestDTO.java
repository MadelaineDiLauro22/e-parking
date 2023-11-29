package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.NotificationType;

public class NotificationRequestDTO {

    private String title;
    private String message;
    private NotificationType type;
    private Long userId;

    public NotificationRequestDTO(String title, String message, NotificationType type, Long userId) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.userId = userId;
    }

    public NotificationRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }
}
