package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.ParkingType;

public class ParkingRegisterDTO {

    private ParkingType parkingType;
    private String vehicle;
    private String vehiclePic;
    private String ticketPic;
    private Double lat;
    private Double ln;

    public ParkingRegisterDTO() {
    }

    public ParkingType parkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public String vehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String vehiclePic() {
        return vehiclePic;
    }

    public void setVehiclePic(String vehiclePic) {
        this.vehiclePic = vehiclePic;
    }

    public String ticketPic() {
        return ticketPic;
    }

    public void setTicketPic(String ticketPic) {
        this.ticketPic = ticketPic;
    }

    public Double lat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double ln() {
        return ln;
    }

    public void setLn(Double ln) {
        this.ln = ln;
    }
}
