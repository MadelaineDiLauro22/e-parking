package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.UserRole;

public class LoginDataDTO {

    private UserRole rol = UserRole.USER;

    private String name;

    private String nickName;
    private String email;
    private String password;

    public LoginDataDTO() {
    }

    public LoginDataDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRole getRol() {
        return rol;
    }

    public void setRol(UserRole rol) {
        this.rol = rol;
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

