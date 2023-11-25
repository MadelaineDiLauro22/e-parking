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
        Vehicle vehicle2 = new Vehicle("123", "Fiat", "Fitito", "Rojo");

        vehicle.setUser(user);
        vehicle2.setUser(admin);

        Geolocation geolocation = new Geolocation(-34.670560, -58.562780);
        Garage garage = new Garage("Pepe", 30, geolocation, "Florencio Varela 1903, B1754JEE San Justo, Buenos Aires Province, Argentina", 1.5F, 1.0F, (long) 1.0);
        garage.setUser(garageUser);
        garage.addVehicle(vehicle2.getPatent());

        Parking parking = new Parking(ParkingType.GARAGE, null, null, garage.getGeolocation(), Date.from(Instant.now()));
        parking.setMobileUser(admin);
        parking.setVehicle(vehicle2);
        List<Parking> parkingList = new ArrayList<>();
        parkingList.add(parking);
        admin.setParkings(parkingList);

        user.registerVehicle(vehicle);
        admin.registerVehicle(vehicle2);

        userRepository.save(user);
        userRepository.save(admin);

        createAndSaveParkingsPlaces();

        userRepository.save(garageUser);
        parkingPlaceRepository.save(garage);
        parkingRepository.save(parking);

        Report report = new Report(ReportType.FRAUD, "Example Report", garage, user);

        reportRepository.save(report);
    }

    private void createAndSaveParkingsPlaces() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PointSale> pointsSale = objectMapper.readValue(new File("src/main/resources/PointSaleList.json"), new TypeReference<List<PointSale>>() {
        });

        for (PointSale pointSale : pointsSale) {
            parkingPlaceRepository.save(pointSale);
        }
    }

}