package com.tallerwebi.model;

import javax.persistence.*;

@Entity
@Table(name = "USER_TABLE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole rol;
    private Boolean activo = false;

    public Usuario(String email, String password, UserRole rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public Usuario() {
    }

    public abstract void registerVehicle(Vehiculo vehiculo);

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
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }
}
