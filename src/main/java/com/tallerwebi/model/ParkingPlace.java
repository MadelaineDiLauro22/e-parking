package com.tallerwebi.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "PARKING_PLACE")
@Inheritance(strategy = InheritanceType.JOINED)
public  abstract class ParkingPlace {

    @Id
    @Column(name = "parking_place_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Embedded
    private Geolocation geolocation;

    private float feePerHour;

    private float feeFraction;

    private long fractionTime;

    @OneToMany(mappedBy = "parking_place", fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    public ParkingPlace(String name, Geolocation geolocation, float feePerHour, float feeFraction, long fractionTime) {
        this.name = name;
        this.geolocation = geolocation;
        this.feePerHour = feePerHour;
        this.feeFraction = feeFraction;
        this.fractionTime = fractionTime;
    }

    public ParkingPlace() {
    }

    public Ticket generateTicket(Parking parking){
        Ticket ticket = new Ticket();
        ticket.setPaid(false);
        ticket.setParking_place(this);
        ticket.setAmmountHs((int) (parking.getDateExit().getTime() - parking.getDateArrival().getTime()));
        return ticket;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public float getFeePerHour() {
        return feePerHour;
    }

    public void setFeePerHour(float feePerHour) {
        this.feePerHour = feePerHour;
    }

    public float getFeeFraction() {
        return feeFraction;
    }

    public void setFeeFraction(float feeFraction) {
        this.feeFraction = feeFraction;
    }

    public long getFractionTime() {
        return fractionTime;
    }

    public void setFractionTime(long fractionTime) {
        this.fractionTime = fractionTime;
    }
}
