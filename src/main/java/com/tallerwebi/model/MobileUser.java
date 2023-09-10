package com.tallerwebi.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MOBILE_USER")
@PrimaryKeyJoinColumn(name = "user_id")
public class MobileUser extends User {

    private String name;
    @Column(name = "nick_name")
    private String nickName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    public MobileUser(String email, String password, UserRole rol, String name, String nickName) {
        super(email, password, rol);
        this.name = name;
        this.nickName = nickName;
        this.vehicles = new ArrayList<>();
    }

    public MobileUser() {
        super();
    }

    @Override
    public void registerVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
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
}
