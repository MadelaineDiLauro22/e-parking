package com.tallerwebi.infraestructura;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.model.Garage;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.ParkingPlace;
import com.tallerwebi.model.PointSale;
import org.hibernate.Criteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
class ParkingPlaceRepositoryImplTest {

    @Autowired
    private ParkingPlaceRepository parkingPlaceRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Rollback
    @Test
    public void whenRegisterParkingPlace_shouldSaveSuccesfully(){
        ParkingPlace parkingPlace = new PointSale();

        parkingPlaceRepository.save(parkingPlace);

        assertEquals(parkingPlace, parkingPlaceRepository.findById(parkingPlace.getId()));
    }

    @Transactional
    @Rollback
    @Test
    public void whenNeedAllParkingsPlaces_ReturnListOfParkingPlaces(){
        List<ParkingPlace> list = saveInRepositoryAndReturnTheListOfParkingPlaces();

        assertEquals(list, parkingPlaceRepository.findAll());
    }

    private List<ParkingPlace> saveInRepositoryAndReturnTheListOfParkingPlaces() {
        List<ParkingPlace> list = new ArrayList<>();

        ParkingPlace parkingPlace = new PointSale();
        ParkingPlace parkingPlace2 = new PointSale();
        ParkingPlace parkingPlace3 = new PointSale();

        list.add(parkingPlace);
        list.add(parkingPlace2);
        list.add(parkingPlace3);

        parkingPlaceRepository.save(parkingPlace);
        parkingPlaceRepository.save(parkingPlace2);
        parkingPlaceRepository.save(parkingPlace3);
        return list;
    }
    @Transactional
    @Rollback
    @Test
    public void testFindGarageByUser() {
        Garage garage = new Garage();
        MobileUser user = new MobileUser();
        garage.setUser(user);

        userRepository.save(user);
        parkingPlaceRepository.save(garage);
        Garage response = (Garage) parkingPlaceRepository.findGarageByUser(user);
        assertEquals(garage, response);
    }
}