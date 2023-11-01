package com.tallerwebi.model;

import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "GARAGE")
@PrimaryKeyJoinColumn(name = "parking_place_id")
public class Garage extends ParkingPlace {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "garage_id")
    private User user;

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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}