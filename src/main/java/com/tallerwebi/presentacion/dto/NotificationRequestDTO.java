package com.tallerwebi.presentacion.dto;

public class NotificationRequestDTO {

    private String title;
    private String message;

    public NotificationRequestDTO(String title, String message) {
        this.title = title;
        this.message = message;
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
