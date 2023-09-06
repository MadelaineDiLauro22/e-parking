package com.tallerwebi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MOBILE_USER")
@PrimaryKeyJoinColumn(name = "user_id")
public class MobileUser extends Usuario {

    private String nombre;
    @Column(name = "nick_name")
    private String nickName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;

    public MobileUser(String email, String password, String rol, String nombre, String nickName) {
        super(email, password, rol);
        this.nombre = nombre;
        this.nickName = nickName;
    }

    public MobileUser() {
        super();
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
}
