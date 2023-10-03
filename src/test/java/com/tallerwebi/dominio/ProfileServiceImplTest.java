package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceImplTest {

    private ProfileServiceImpl profileService;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ParkingRepository mockParkingRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Captor
    private ArgumentCaptor<Parking> parkingCaptor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileService = new ProfileServiceImpl(mockVehicleRepository, mockUserRepository, mockParkingRepository, notificationRepository);
    }

    @Test
    void shouldGetVehiclesAndParkingList(){
        Long userId = 1L;
        List<Vehicle> vehiclesList = new ArrayList<>();
        List<Parking> parkingsList = new ArrayList<>();
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();
        Parking parking = new Parking();
        vehiclesList.add(vehicle);
        parkingsList.add(parking);

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehiclesByUser(user))
                .thenReturn(vehiclesList);
        Mockito.when(mockParkingRepository.findParkingsByUser(user)).
                thenReturn(parkingsList);

        ProfileResponseDTO response = profileService.getVehiclesAndParkingsByMobileUser(userId);

        assertEquals(vehiclesList, response.getVehicles());
        assertEquals(parkingsList, response.getParkings());
    }

    @Test
    void whenTryToGetVehiclesAndParkingsByMobileUser_IfUserNotExist_ShouldThrowUserNotFoundException(){
        Long userId = 1L;

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> profileService.getVehiclesAndParkingsByMobileUser(userId));
    }

    @Test
    void whenTryToGetAllNotificationsByMobileUser_ShouldGetAllNotifications() {
        MobileUser user = getNewMobileUserWithNotificationList();
        List<Notification> notifications = user.getNotifications();

        Mockito.when(mockUserRepository.findUserById(user.getId()))
                .thenReturn(user);
        Mockito.when(notificationRepository.findAllByUser(user))
                .thenReturn(user.getNotifications());

        assertEquals(notifications, notificationRepository.findAllByUser(user));
    }

    private MobileUser getNewMobileUserWithNotificationList(){
        Notification notification = new Notification();
        MobileUser user = new MobileUser();
        user.addNotification(notification);

        return user;
    }
}