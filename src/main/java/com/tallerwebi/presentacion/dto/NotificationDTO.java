package com.tallerwebi.presentacion.dto;

public class NotificationDTO {

    private int count;

    private String lastMessage;

    public NotificationDTO(int count, String lastMessage) {
        this.count = count;
        this.lastMessage = lastMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
