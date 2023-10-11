package com.tallerwebi.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "MOBILE_USER")
@PrimaryKeyJoinColumn(name = "user_id")
public class MobileUser extends User {

    private String name;
    @Column(name = "nick_name")
    private String nickName;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Parking> parkings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notifications;

    public MobileUser(String email, String password, UserRole rol, String name, String nickName) {
        super(email, password, rol);
        this.name = name;
        this.nickName = nickName;
        this.vehicles = new HashSet<>();
        this.parkings = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public MobileUser() {
        super();
        this.vehicles = new HashSet<>();
        this.parkings = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void registerVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void registerParking(Parking parking) {
        parkings.add(parking);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Parking> getParkings() {
        Collections.sort(parkings);
        return parkings;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }


}
