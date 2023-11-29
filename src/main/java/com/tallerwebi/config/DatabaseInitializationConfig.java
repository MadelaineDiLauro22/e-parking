package com.tallerwebi.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.ReportRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class DatabaseInitializationConfig {

    @Value("${basic.user.mail}")
    public String mail;
    public static final String PASSWORD = "test";
    public static final String NOMBRE = "Admin";
    public static final String NICK_NAME = "admin";
    public static final String PATENTE = "ABC123";
    public static final String MARCA = "VOLKSWAGEN";
    public static final String MODELO = "FOX";
    public static final String COLOR = "Rojo";
    private static final String MAIL_ADMIN = "admin@unlam.edu.ar";
    private static final String MAIL_GARAGE = "garage@unlam.edu.ar";
    private final UserRepository userRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ParkingRepository parkingRepository;
    private final ReportRepository reportRepository;

    @Value("${garage.time.fraction}")
    private long garageFractionTime;
    @Value("${garage.price.fraction}")
    private float garagePriceFraction;
    @Value("${garage.price.hour}")
    private float garagePriceHour;

    public DatabaseInitializationConfig(UserRepository userRepository, ParkingPlaceRepository parkingPlaceRepository, ParkingRepository parkingRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.parkingRepository = parkingRepository;
        this.reportRepository = reportRepository;
    }

    @Bean
    @Transactional
    public void dataSourceInitializer() throws IOException {
        MobileUser user = new MobileUser(mail, PASSWORD, UserRole.USER, NOMBRE, NICK_NAME);
        MobileUser admin = new MobileUser(MAIL_ADMIN, PASSWORD, UserRole.ADMIN, NOMBRE, NICK_NAME);
        MobileUser garageUser = new MobileUser(MAIL_GARAGE, PASSWORD, UserRole.ADMIN_GARAGE, NOMBRE, NICK_NAME);

        Vehicle vehicle = new Vehicle(PATENTE, MARCA, MODELO, COLOR);
        Vehicle vehicle2 = new Vehicle("CBD123", "Fiat", "Fitito", "Rojo");

        vehicle.setUser(user);
        vehicle2.setUser(admin);

        Geolocation geolocation = new Geolocation(-34.670560, -58.562780);
        Garage garage = new Garage("Lo de Pepe", 30, geolocation, "Florencio Varela 1903, B1754JEE San Justo, Buenos Aires Province, Argentina", garagePriceHour, garagePriceFraction, garageFractionTime);
        garage.setUser(garageUser);

        user.registerVehicle(vehicle);
        admin.registerVehicle(vehicle2);

        userRepository.save(user);
        userRepository.save(admin);

        createAndSaveParkingsPlaces();

        userRepository.save(garageUser);
        parkingPlaceRepository.save(garage);
    }

    public void createAndSaveParkingsPlaces() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PointSale> pointsSaleList = objectMapper.readValue(new File("src/main/resources/PointSaleList.json"), new TypeReference<List<PointSale>>() {
        });

        for (PointSale pointSale : pointsSaleList) {
            parkingPlaceRepository.save(pointSale);
        }

        Geolocation geo1 = new Geolocation(-34.686558, -58.543516);
        Geolocation geo2 = new Geolocation(-34.6877562324958, -58.53428406521504);
        Geolocation geo3 = new Geolocation(-34.687634, -58.550955);

        Garage garage1 = new Garage("Garage 1", 0, geo1, "AGL, Adolfo Berro 2135, B1766 La Tablada", 500.0F, 200.0F, 30);
        Garage garage2 = new Garage("Garage 2", 0, geo2, "EQV La Tablada Buenos Aires AR, Rincón 5480, B1766", 600.0F, 400.0F, 45);
        Garage garage3 = new Garage("Garage 3", 0, geo3, "Yeruá 2652, B1754GHJ San Justo, Provincia de Buenos Aires", 750.0F, 300.0F, 40);
        parkingPlaceRepository.save(garage1);
        parkingPlaceRepository.save(garage2);
        parkingPlaceRepository.save(garage3);
    }

}