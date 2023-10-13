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


    public Ticket(long id, int ammountHs, boolean isPaid) {
        this.id = id;
        this.ammountHs = ammountHs;
        this.isPaid = isPaid;
    }

    public Ticket() {
    }

    public int getAmmountHs() {
        return ammountHs;
    }

    public void setAmmountHs(int ammountHs) {
        this.ammountHs = ammountHs;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public ParkingPlace getParking_place() {
        return parking_place;
    }

    public void setParking_place(ParkingPlace parking_place) {
        this.parking_place = parking_place;
    }
}
