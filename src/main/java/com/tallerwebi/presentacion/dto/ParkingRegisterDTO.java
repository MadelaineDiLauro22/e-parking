package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.ParkingType;

public class ParkingRegisterDTO {

    private ParkingType parkingType;
    private String vehicle;
    private byte[] vehiclePic;
    private byte[] ticketPic;
    private Double lat;
    private Double ln;

    public ParkingRegisterDTO() {
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public byte[] getVehiclePic() {
        return vehiclePic;
    }

    public void setVehiclePic(byte[] vehiclePic) {
        this.vehiclePic = vehiclePic;
    }

    public byte[] getTicketPic() {
        return ticketPic;
    }

    public void setTicketPic(byte[] ticketPic) {
        this.ticketPic = ticketPic;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLn() {
        return ln;
    }

    public void setLn(Double ln) {
        this.ln = ln;
    }
}
