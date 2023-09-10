package com.tallerwebi.config;

import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.UserRole;
import com.tallerwebi.model.Vehiculo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializationConfig {

    public static final String MAIL = "test@unlam.edu.ar";
    public static final String PASSWORD = "test";
    public static final String NOMBRE = "Admin";
    public static final String NICK_NAME = "admin";
    public static final String PATENTE = "ABC123";
    public static final String MARCA = "VOLKSWAGEN";
    public static final String MODELO = "FOX";
    public static final String COLOR = "Rojo";
    private final RepositorioUsuario repositorioUsuario;


    public DatabaseInitializationConfig(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Bean
    public void dataSourceInitializer() {
        MobileUser user = new MobileUser(MAIL, PASSWORD, UserRole.ADMIN, NOMBRE, NICK_NAME);
        Vehiculo vehiculo = new Vehiculo(PATENTE, MARCA, MODELO, COLOR);
        user.registerVehicle(vehiculo);

        repositorioUsuario.guardar(user);
    }

}