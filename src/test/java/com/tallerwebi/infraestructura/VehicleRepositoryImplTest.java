package com.tallerwebi.infraestructura;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
class VehicleRepositoryImplTest {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Rollback
    @Test
    void shouldSaveVehicle() {
        Vehicle vehicle = new Vehicle("ABC123", "BMW", "2023", "BLACK");

        vehicleRepository.save(vehicle);

        Vehicle saved = vehicleRepository.findVehicleByPatent(vehicle.getPatent());

        assertEquals(vehicle, saved);
    }

    @Transactional
    @Rollback
    @Test
    void shouldFindAVehicleByPatent(){
        String patent = "ABC123";

        Vehicle vehicle = new Vehicle("ABC123", "BMW", "2023", "BLACK");

        vehicleRepository.save(vehicle);

        Vehicle saved = vehicleRepository.findVehicleByPatent(patent);

        assertEquals(vehicle, saved);
    }

    @Transactional
    @Rollback
    @Test
    void shouldFindVehiclesByUserId(){

        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle("ABC123", "BMW", "2023", "BLACK");
        Vehicle vehicle2 = new Vehicle("DEF123", "MERCEDEZ", "2022", "RED");

        vehicle.setUser(user);
        vehicle2.setUser(user);
        user.registerVehicle(vehicle);
        user.registerVehicle(vehicle2);

        userRepository.save(user);
        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle2);

        List<Vehicle> vehicleList = vehicleRepository.findVehiclesByUser(user);

        assertEquals(vehicleList.size(), 2);
    }

}