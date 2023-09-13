package com.tallerwebi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PARKING")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private ParkingType parkingType;

    private byte[] vehiclePicture;

    private byte[] ticketPicture;

    @Embedded
    private Geolocation geolocation;

    private Date dateArrival;

    private Date dateExit;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MobileUser mobileUser;

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

    public void setName(String name) {
        this.name = name;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public byte[] getVehiclePicture() {
        return vehiclePicture;
    }

    public void setVehiclePicture(byte[] vehiclePicture) {
        this.vehiclePicture = vehiclePicture;
    }

    public byte[] getTicketPicture() {
        return ticketPicture;
    }

    public void setTicketPicture(byte[] ticketPicture) {
        this.ticketPicture = ticketPicture;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public Date getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(Date dateArrival) {
        this.dateArrival = dateArrival;
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
        return mobileUser;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setMobileUser(MobileUser mobileUser) {
        this.mobileUser = mobileUser;
    }
}
