package com.tallerwebi.model;

import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "GARAGE")
@PrimaryKeyJoinColumn(name = "parking_place_id")
public class Garage extends ParkingPlace {

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    public Garage(String name, Geolocation geolocation, float feePerHour, float feeFraction, long fractionTime) {
        super(name, geolocation, feePerHour, feeFraction, fractionTime);
    }

    public Garage() {
    }

    public boolean addVehicle(Vehicle vehicle){
        return vehicles.add(vehicle);
    }

    public boolean removeVehicle(String patent){
        for(Vehicle vehicle : vehicles){
            if(vehicle.getPatent().equalsIgnoreCase(patent)){
                return vehicles.remove(vehicle);
            }
        }
        return false;
    }
    @Override
    public Ticket generateTicket(ParkingRegisterDTO parking) {
        Ticket ticket = new Ticket(parking.getAmmountHs(), parking.isPaid(), this);
        super.getTickets().add(ticket);
        return ticket;
    }
}
