package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.NotificationType;

public class NotificationRequestDTO {

    private String title;
    private String message;
    private NotificationType type;

    public NotificationRequestDTO(String title, String message, NotificationType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public NotificationRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
