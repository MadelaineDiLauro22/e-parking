package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.ParkingType;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;
import java.time.Instant;
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
    @Captor
    private ArgumentCaptor<Parking> parkingCaptor;

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
        Vehicle vehicle = new Vehicle();
        cars.add(vehicle);

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
    void shouldRegisterParking() {
        java.util.Date date = Date.from(Instant.now());
        ParkingRegisterDTO dto = new ParkingRegisterDTO(
                ParkingType.STREET,
                "ABC123",
                null,
                null,
                (double) 0,
                (double) 0
        );
        Long idUser = 1L;
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();

        Mockito.when(mockUserRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehicleByPatent("ABC123"))
                .thenReturn(vehicle);

        parkingService.registerParking(dto, idUser);
        Mockito.verify(mockUserRepository).save(user);
        Mockito.verify(mockParkingRepository).save(parkingCaptor.capture());

        Parking registered = parkingCaptor.getValue();

        assertEquals(ParkingType.STREET, registered.getParkingType());
        assertEquals(vehicle, registered.getVehicle());
        assertEquals(user, registered.getMobileUser());
        assertEquals(dto.getParkingDate(),registered.getDateArrival());
    }

    @Test
    void whenRegisterParking_ifUserNotFound_shouldThrowException() {
        ParkingRegisterDTO dto = new ParkingRegisterDTO();
        Long userId = 1L;

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class,
                () -> parkingService.registerParking(dto, userId));
    }

    @Test
    void whenRegisterParking_ifVehicleNotFound_shouldThrowException() {
        String patent = "ABC123";
        ParkingRegisterDTO dto = new ParkingRegisterDTO();
        dto.setVehicle(patent);
        Long userId = 1L;

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(new MobileUser());
        Mockito.when(mockVehicleRepository.findVehicleByPatent(patent))
                .thenThrow(new VehicleNotFoundException());

        assertThrows(VehicleNotFoundException.class,
                () -> parkingService.registerParking(dto, userId));
    }

    @Test
    void whenGetParkingInformation_ifUserNotHaveVehicles_shouldThrowException() {
        Long userId = 1L;
        MobileUser user = new MobileUser();

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehiclesByUser(user))
                .thenReturn(List.of());

        assertThrows(VehicleNotFoundException.class,
                () -> parkingService.getUserCarsList(userId));
    }

}