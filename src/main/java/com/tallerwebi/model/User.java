package com.tallerwebi.model;

import javax.persistence.*;

@Entity
@Table(name = "USER_TABLE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole rol;
    private Boolean isActive = false;

    public User(String email, String password, UserRole rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public User() {
    }

    public abstract void registerVehicle(Vehicle vehicle);
    public abstract void registerParking(Parking parking);

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserRole getRol() {
        return rol;
    }
    public void setRol(UserRole rol) {
        this.rol = rol;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean activo) {
        this.isActive = activo;
    }

}
