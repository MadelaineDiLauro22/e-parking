package com.tallerwebi.model;

public class Geolocation {
    private Double lat;
    private Double ln;

    public Geolocation(Double lat, Double ln){
        this.lat = lat;
        this.ln = ln;
    }

    public Geolocation(){

    }

    public Double getLat() {
        return lat;
    }

    public Double getLn() {
        return ln;
    }

    public String toString(){
        return lat + "," + ln;
    }
}
