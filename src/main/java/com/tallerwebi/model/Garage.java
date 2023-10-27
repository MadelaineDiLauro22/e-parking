package com.tallerwebi.model;

import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "GARAGE")
@PrimaryKeyJoinColumn(name = "parking_place_id")
public class Garage extends ParkingPlace {

    @ElementCollection
    private List<String> vehicles;


    public Garage(String name, Geolocation geolocation, float feePerHour, float feeFraction, long fractionTime) {
        super(name, geolocation, feePerHour, feeFraction, fractionTime);
        this.vehicles = new ArrayList<>();
    }

    public Garage() {
    }

    public boolean addVehicle(String patent){
        return vehicles.add(patent);
    }

    public boolean removeVehicle(String patent){
        return vehicles.remove(patent);
    }
    @Override
    public Ticket generateTicket(ParkingRegisterDTO parking) {
        Ticket ticket = new Ticket(parking.getAmmountHs(), parking.isPaid(), this);
        super.getTickets().add(ticket);
        return ticket;
    }

    public List<String> getVehicles() {
        return vehicles;
    }
}
