package com.tallerwebi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PARKING")
public class Parking implements Comparable<Parking>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private ParkingType parkingType;

    @Lob
    @Column(name = "vehicle_picture")
    private byte[] vehiclePicture;

    @Lob
    @Column(name = "ticket_picture")
    private byte[] ticketPicture;

    @Embedded
    private Geolocation geolocation;

    private Date dateArrival;

    private Date dateExit;
    @JsonManagedReference
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MobileUser user;

    public Parking() {
    }
    public Parking(ParkingType parkingType, byte[] vehiclePicture, byte[] ticketPicture, Geolocation geolocation, Date dateArrival) {
        this.parkingType = parkingType;
        this.vehiclePicture = vehiclePicture;
        this.ticketPicture = ticketPicture;
        this.geolocation = geolocation;
        this.dateArrival = dateArrival;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public byte[] getVehiclePicture() {
        return vehiclePicture;
    }

    public byte[] getTicketPicture() {
        return ticketPicture;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public Date getDateArrival() {
        return dateArrival;
    }

    public Date getDateExit() {
        return dateExit;
    }

    public void setDateExit(Date dateExit) {
        this.dateExit = dateExit;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public MobileUser getMobileUser() {
        return user;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setTicket(Ticket ticket) {this.ticket = ticket;}
    public Ticket getTicket(){
        return ticket;
    }

    public void setMobileUser(MobileUser mobileUser) {
        this.user = mobileUser;
    }

    @Override
    public int compareTo(Parking parking) {
        return parking.dateArrival.compareTo(this.dateArrival);
    }

    public boolean hasTicket() {
        return ticketPicture != null && ticketPicture.length > 0;
    }
    public boolean hasVehiclePictures() {
        return vehiclePicture != null && vehiclePicture.length > 0;
    }

    public void setName(String name) {
        this.name = name;
    }
}
