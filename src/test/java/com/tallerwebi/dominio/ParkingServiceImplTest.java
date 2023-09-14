package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ParkingServiceImplTest {

    private ParkingServiceImpl parkingService;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ParkingRepository mockParkingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parkingService = new ParkingServiceImpl(mockVehicleRepository, mockUserRepository, mockParkingRepository);
    }

    @Test
    void shouldGetVehicleList(){
        Long userId = 1L;
        List<Vehicle> cars = new ArrayList<>();
        MobileUser user = new MobileUser();

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(user);

        Mockito.when(mockVehicleRepository.findVehiclesByUser(user))
                .thenReturn(cars);

        List<Vehicle> vehicles = parkingService.getUserCarsList(userId);

        assertEquals(cars,vehicles);
    }

    @Test
    void whenTryToGetUserCarList_IfUserNotExist_ShouldThrowException(){
        Long userId = 1L;

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(null);

        assertThrows(UserNotFoundException.class,() -> parkingService.getUserCarsList(userId));
    }

    @Test
    @Disabled
    void shouldRegisterParking() {
        //TODO: implement test
    }

    @Test
    @Disabled
    void whenRegisterParking_ifUserNotFound_shouldThrowException() {
        //TODO: implement test
    }

    @Test
    @Disabled
    void whenRegisterParking_ifVehicleNotFound_shouldThrowException() {
        //TODO: implement test
    }

}