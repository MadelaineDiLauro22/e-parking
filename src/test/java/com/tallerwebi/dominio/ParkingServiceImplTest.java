package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ParkingRegisterException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.helpers.Alarm;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ParkingPlaceResponseDTO;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;

class ParkingServiceImplTest {

    private ParkingServiceImpl parkingService;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ParkingRepository mockParkingRepository;
    @Mock
    private ParkingPlaceRepository mockParkingPlaceRepository;
    @Mock
    private Alarm mockAlarm;
    @Captor
    private ArgumentCaptor<Parking> parkingCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parkingService = new ParkingServiceImpl(mockVehicleRepository, mockUserRepository, mockParkingRepository, mockParkingPlaceRepository, mockAlarm);
    }

    @Test
    void shouldGetVehicleList() {
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

        assertEquals(cars, vehicles);
    }

    @Test
    void whenTryToGetUserCarList_IfUserNotExist_ShouldThrowException() {
        Long userId = 1L;

        Mockito.when(mockUserRepository.findUserById(userId))
                .thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> parkingService.getUserCarsList(userId));
    }

    @Test
    void shouldRegisterParking() {
        Date date = Date.from(Instant.now());
        ParkingRegisterDTO dto = new ParkingRegisterDTO(
                ParkingType.STREET,
                "ABC123",
                new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]),
                (double) 0,
                (double) 0,
                1L,
                Date.from(Instant.now())
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
        assertEquals(dto.getParkingDate(), registered.getDateArrival());
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

    @Test
    void shouldParkingPlaceList() {
        List<ParkingPlace> parkingPlaces = new ArrayList<>();
        List<ParkingPlaceResponseDTO> parkingPlacesDTO = new ArrayList<>();

        Mockito.when(mockParkingPlaceRepository.findAll())
                .thenReturn(parkingPlaces);

        assertEquals(parkingPlacesDTO, parkingService.getParkingPlaces());
    }

    @Test
    void shouldRegisterParkingAndGeneratePointSaleTicket() {
        Date date = Date.from(Instant.now());
        ParkingRegisterDTO dto = new ParkingRegisterDTO(
                ParkingType.POINT_SALE,
                "ABC123",
                new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]),
                (double) 0,
                (double) 0,
                1L,
                Date.from(Instant.now())
        );
        Long idUser = 1L;
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();
        ParkingPlace pointSale = new PointSale();

        Mockito.when(mockUserRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehicleByPatent("ABC123"))
                .thenReturn(vehicle);
        Mockito.when(mockParkingPlaceRepository.findById(dto.getParkingPlaceId()))
                .thenReturn(pointSale);

        parkingService.registerParking(dto, idUser);
        Mockito.verify(mockUserRepository).save(user);
        Mockito.verify(mockParkingRepository).save(parkingCaptor.capture());

        Parking registered = parkingCaptor.getValue();

        assertEquals(ParkingType.POINT_SALE, registered.getParkingType());
        assertEquals(vehicle, registered.getVehicle());
        assertEquals(user, registered.getMobileUser());
        assertEquals(dto.getParkingDate(), registered.getDateArrival());
        assertNotNull(registered.getTicket());
    }

    @Test
    void shouldRegisterAnAlarm() throws InterruptedException {
        Date alarm = Date.from(Instant.now());
        ParkingRegisterDTO parkingRegisterDTO = createRequestAlarm(alarm);
        long idUser = 1L;

        parkingService.registerParking(parkingRegisterDTO, idUser);

        Mockito.verify(mockAlarm).createAlarm(ZonedDateTime.ofInstant(alarm.toInstant(), ZoneId.of("America/Argentina/Buenos_Aires")), idUser);
    }

    @Test
    void shouldRegisterAnAlarmWithAmountHrsType() throws InterruptedException {
        int hours = 2;
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime newDateTime = dateTime.plusHours(hours);
        ZonedDateTime expectedZonedDateTime = newDateTime.atZone(ZoneId.of("America/Argentina/Buenos_Aires"));
        ParkingRegisterDTO parkingRegisterDTO = createRequestAlarmAmountHrs(hours);
        long idUser = 1L;

        parkingService.registerParking(parkingRegisterDTO, idUser);

        Mockito.verify(mockAlarm).createAlarm(argThat(zonedDateTime ->
                Math.abs(ChronoUnit.MILLIS.between(zonedDateTime, expectedZonedDateTime)) < 1000
        ), anyLong());
    }

    @Test
    void shouldRegisterAnAlarmWithAmountDesired() throws InterruptedException {
        float amount = 1000.0F;
        float hours = amount / 5.0F;
        int addedHours = Math.round(hours);
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime newDateTime = dateTime.plusHours(addedHours);
        ParkingRegisterDTO parkingRegisterDTO = createRequestAlarmAmountDesired(amount);
        ZonedDateTime expectedZonedDateTime = newDateTime.atZone(ZoneId.of("America/Argentina/Buenos_Aires"));
        long idUser = 1L;

        parkingService.registerParking(parkingRegisterDTO, idUser);

        Mockito.verify(mockAlarm).createAlarm(argThat
                (zonedDateTime -> Math.abs(ChronoUnit.MILLIS.between(zonedDateTime, expectedZonedDateTime)) < 1000),
                anyLong()
        );
    }

    @Test
    void whenRegisterAlarm_ifIsEnabledAndDateNull_shouldThrowException() {
        ParkingRegisterDTO parkingRegisterDTO = createRequestAlarm(null);

        assertThrows(ParkingRegisterException.class, () -> parkingService.registerParking(parkingRegisterDTO, 1L));
    }

    private PointSale getPointSale() {
        return new PointSale("point 1", new Geolocation(-23112.32, -3242432.3), "", 20, 20, 20L);
    }

    private ParkingRegisterDTO createRequestAlarm(Date alarm) {
        ParkingRegisterDTO req = new ParkingRegisterDTO(
                ParkingType.STREET,
                "ABC123",
                new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]),
                (double) 0,
                (double) 0,
                1L,
                Date.from(Instant.now())
        );
        Long idUser = 1L;
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();
        req.setEnableAlarm(true);
        req.setAlarmDate(alarm);

        Mockito.when(mockUserRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehicleByPatent("ABC123"))
                .thenReturn(vehicle);

        return req;
    }

    private ParkingRegisterDTO createRequestAlarmAmountHrs(int hours) {
        ParkingRegisterDTO req = new ParkingRegisterDTO(
                ParkingType.STREET,
                "ABC123",
                new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]),
                (double) 0,
                (double) 0,
                1L,
                Date.from(Instant.now())
        );
        Long idUser = 1L;
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();
        req.setEnableAlarm(true);
        req.setAlarmType(AlarmType.AMOUNT_HS);
        req.setAmmountHrsAlarm(hours);

        Mockito.when(mockUserRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehicleByPatent("ABC123"))
                .thenReturn(vehicle);

        return req;
    }

    private ParkingRegisterDTO createRequestAlarmAmountDesired(float amount) {
        ParkingRegisterDTO req = new ParkingRegisterDTO(
                ParkingType.STREET,
                "ABC123",
                new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]),
                (double) 0,
                (double) 0,
                1L,
                Date.from(Instant.now())
        );
        Long idUser = 1L;
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();
        req.setEnableAlarm(true);
        req.setAlarmType(AlarmType.AMOUNT_DESIRED);
        req.setAmountDesired(amount);

        Geolocation geolocation = new Geolocation(34.052235, -118.243683);
        String name = "Downtown Parking";
        float feePerHour = 5.0f;
        float feeFraction = 1.25f;
        long fractionTime = 15;
        PointSale pointSale = new PointSale(name, geolocation, "", feePerHour, feeFraction, fractionTime);


        Mockito.when(mockUserRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(mockVehicleRepository.findVehicleByPatent("ABC123"))
                .thenReturn(vehicle);
        Mockito.when(mockParkingPlaceRepository.findById(anyLong())).thenReturn(pointSale);
        return req;
    }
}