package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.AlarmType;
import com.tallerwebi.model.ParkingPlace;
import com.tallerwebi.model.ParkingType;
import com.tallerwebi.model.PointSale;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class ParkingRegisterDTO {

    private ParkingType parkingType;
    private String vehicle;
    private MultipartFile vehiclePic;
    private MultipartFile ticketPic;
    private Double lat;
    private Double ln;
    //@Temporal(TemporalType.DATE)
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date parkingDate;
    private int ammountHs;
    private int ammountHrsAlarm;
    private float amountDesired;
    private AlarmType alarmType = AlarmType.NORMAL;
    private boolean isPaid = false;
    private Long parkingPlaceId;
    private boolean enableAlarm = false;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date alarmDate;

    public ParkingRegisterDTO() {
    }

    public ParkingRegisterDTO(ParkingType parkingType, String vehicle, MultipartFile vehiclePic, MultipartFile ticketPic, Double lat, Double ln, Long parkingPlaceId, Date parkingDate) {
        this.parkingType = parkingType;
        this.vehicle = vehicle;
        this.vehiclePic = vehiclePic;
        this.ticketPic = ticketPic;
        this.lat = lat;
        this.ln = ln;
        this.parkingDate = parkingDate;
        this.parkingPlaceId = parkingPlaceId;


    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public MultipartFile getVehiclePic() {
        return vehiclePic;
    }

    public void setVehiclePic(MultipartFile vehiclePic) {
        this.vehiclePic = vehiclePic;
    }

    public MultipartFile getTicketPic() {
        return ticketPic;
    }

    public void setTicketPic(MultipartFile ticketPic) {
        this.ticketPic = ticketPic;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLn() {
        return ln;
    }

    public void setLn(Double ln) {
        this.ln = ln;
    }

    public Date getParkingDate() {
        return parkingDate;
    }

    public void setParkingDate(Date parkingDate) {
        this.parkingDate = parkingDate;
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
    public Long getParkingPlaceId(){
        return parkingPlaceId;
    }

    public void setParkingPlaceId(Long parkingPlaceId) {
        this.parkingPlaceId = parkingPlaceId;
    }

    public boolean isEnableAlarm() {
        return enableAlarm;
    }

    public void setEnableAlarm(boolean enableAlarm) {
        this.enableAlarm = enableAlarm;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public int getAmmountHrsAlarm() {
        return ammountHrsAlarm;
    }
    public void setAmmountHrsAlarm(int ammountHrsAlarm) {
        this.ammountHrsAlarm = ammountHrsAlarm;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }
    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public float getAmountDesired() {
        return amountDesired;
    }

    public void setAmountDesired(float amountDesired) {
        this.amountDesired = amountDesired;
    }

}
