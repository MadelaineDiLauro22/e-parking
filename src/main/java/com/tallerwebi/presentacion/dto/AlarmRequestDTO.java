package com.tallerwebi.presentacion.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

public class AlarmRequestDTO {
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime date;

    public AlarmRequestDTO(LocalDateTime date) {
        this.date = date;
    }

    public AlarmRequestDTO() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
