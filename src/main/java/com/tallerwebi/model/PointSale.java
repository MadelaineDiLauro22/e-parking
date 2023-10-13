package com.tallerwebi.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "POINT_SALE")
@PrimaryKeyJoinColumn(name = "parking_place_id")
public class PointSale extends ParkingPlace {

    public PointSale(String name, Geolocation geolocation, float feePerHour, float feeFraction, long fractionTime) {
        super(name, geolocation, feePerHour, feeFraction, fractionTime);
    }

    public PointSale() {
    }
}
