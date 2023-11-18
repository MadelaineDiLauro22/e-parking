package com.tallerwebi.model;

import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GARAGE")
@PrimaryKeyJoinColumn(name = "parking_place_id")
public class Garage extends ParkingPlace {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private MobileUser user;
    @ElementCollection
    private Set<String> patents;
    private int numberOfCars;


    public Garage(String name, int numberOfCars, Geolocation geolocation, String address, float feePerHour, float feeFraction, long fractionTime) {
        super(name, geolocation, address, feePerHour, feeFraction, fractionTime);
        this.patents = new HashSet<>();
        this.numberOfCars = numberOfCars;
    }

    public Garage() {
        this.patents = new HashSet<>();
    }

    public boolean addVehicle(String patent){
        return patents.add(patent);
    }

    public boolean removeVehicle(String patent){
        return patents.remove(patent);
    }
    @Override
    public Ticket generateTicket(ParkingRegisterDTO parking) {
        Ticket ticket = new Ticket(parking.getAmmountHs(), parking.isPaid(), this);
        super.getTickets().add(ticket);
        return ticket;
    }

    public Set<String> getPatents() {
        return patents;
    }

    public MobileUser getUser() {
        return user;
    }
    public void setUser(MobileUser user) {
        this.user = user;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }
}