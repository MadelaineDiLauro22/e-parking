package com.tallerwebi.model;

import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PARKING_PLACE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ParkingPlace {

    @Id
    @Column(name = "parking_place_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Embedded
    private Geolocation geolocation;

    private String address;

    private float feePerHour;

    private float feeFraction;

    private long fractionTime; //in minutes

    @OneToMany(mappedBy = "parking_place", fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    public ParkingPlace(String name, Geolocation geolocation, String address, float feePerHour, float feeFraction, long fractionTime) {
        this.name = name;
        this.geolocation = geolocation;
        this.address = address;
        this.feePerHour = feePerHour;
        this.feeFraction = feeFraction;
        this.fractionTime = fractionTime;
        this.tickets = new ArrayList<>();
    }

    public ParkingPlace() {
        this.tickets = new ArrayList<>();
    }

    public abstract Ticket generateTicket(ParkingRegisterDTO parking);

    public String getName() {
        return name;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public float getFeePerHour() {
        return feePerHour;
    }

    public float getFeeFraction() {
        return feeFraction;
    }

    public long getFractionTime() {
        return fractionTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
