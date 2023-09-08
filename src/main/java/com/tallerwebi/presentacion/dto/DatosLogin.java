package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.UserRole;

public class DatosLogin {

    private UserRole rol = UserRole.USER;

    private String nombre;

    private String nickName;
    private String email;
    private String password;

    public DatosLogin() {
    }

    public DatosLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRole getRol() {
        return rol;
    }

    public void setRol(UserRole rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

}

