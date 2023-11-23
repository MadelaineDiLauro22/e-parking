package com.tallerwebi.presentacion.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ParkingEgressDTO {

    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date dateArrival;
    private Long expendHours;
    private Double expendPrice;


    public ParkingEgressDTO(Date dateArrival, Long expendHours, Double expendPrice) {
        this.dateArrival = dateArrival;
        this.expendHours = expendHours;
        this.expendPrice = expendPrice;
    }

    public Date getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(Date dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Long getExpendHours() {
        return expendHours;
    }

    public void setExpendHours(Long expendHours) {
        this.expendHours = expendHours;
    }

    public Double getExpendPrice() {
        return expendPrice;
    }

    public void setExpendPrice(Double expendPrice) {
        this.expendPrice = expendPrice;
    }
}
