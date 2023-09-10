package com.tallerwebi.config;

import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.UserRole;
import com.tallerwebi.model.Vehicle;
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
    private final UserRepository userRepository;


    public DatabaseInitializationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public void dataSourceInitializer() {
        MobileUser user = new MobileUser(MAIL, PASSWORD, UserRole.ADMIN, NOMBRE, NICK_NAME);
        Vehicle vehicle = new Vehicle(PATENTE, MARCA, MODELO, COLOR);
        user.registerVehicle(vehicle);

        userRepository.save(user);
    }

}