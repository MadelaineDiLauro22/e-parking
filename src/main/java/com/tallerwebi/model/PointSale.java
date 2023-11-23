package com.tallerwebi.model;

import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "POINT_SALE")
@PrimaryKeyJoinColumn(name = "parking_place_id")
public class PointSale extends ParkingPlace {

    public PointSale(String name, Geolocation geolocation, String address, float feePerHour, float feeFraction, long fractionTime) {
        super(name, geolocation, address, feePerHour, feeFraction, fractionTime);
    }

    public PointSale() {
    }

    @Override
    public Ticket generateTicket(ParkingRegisterDTO parking) {
        Ticket ticket = new Ticket(parking.getAmmountHs(), parking.isPaid(), this);
        super.getTickets().add(ticket);
        return ticket;
    }
}
