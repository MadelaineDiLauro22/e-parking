package com.tallerwebi.model;

import javax.persistence.*;

@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int ammountHs;

    private boolean isPaid;

    @JoinColumn(name = "parking_place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParkingPlace parking_place;

    public Ticket(int ammountHs, boolean isPaid, ParkingPlace parking_place) {
        this.ammountHs = ammountHs;
        this.isPaid = isPaid;
        this.parking_place = parking_place;
    }

    public Ticket() {
    }

    public int getAmmountHs() {
        return ammountHs;
    }


    public boolean isPaid() {
        return isPaid;
    }


    public ParkingPlace getParking_place() {
        return parking_place;
    }

}
