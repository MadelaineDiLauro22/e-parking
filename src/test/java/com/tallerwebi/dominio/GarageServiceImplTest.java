package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.OTPNotFoundException;
import com.tallerwebi.helpers.EmailService;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.OTPDTO;
import com.tallerwebi.presentacion.dto.ParkingEgressDTO;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GarageServiceImplTest {

    private GarageService garageService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ParkingPlaceRepository parkingPlaceRepository;
    @Mock
    private ParkingRepository parkingRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private OTPRepository otpRepository;

    @Captor
    private ArgumentCaptor<Parking> parkingArgumentCaptor;

    @Captor
    private ArgumentCaptor<OTP> otpArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        garageService = new GarageServiceImpl(userRepository, vehicleRepository, parkingPlaceRepository, parkingRepository, emailService, otpRepository);
    }

    @Test
    public void testConstructorInitialization() {
        MockitoAnnotations.openMocks(this);
        GarageService garageService = new GarageServiceImpl(userRepository, vehicleRepository, parkingPlaceRepository, parkingRepository, emailService, otpRepository);
        assertNotNull(garageService);
    }

    @Test
    public void testRegisterVehicleSuccess() {
        VehicleIngressDTO dto = new VehicleIngressDTO();
        dto.setPatent("ABC123");
        Long idUser = 1L;
        MobileUser user = new MobileUser();
        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        Garage garage = new Garage();
        OTPDTO otp = new OTPDTO("123");

        Mockito.when(userRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(vehicleRepository.findVehicleByPatent("ABC123"))
                .thenReturn(vehicle);
        Mockito.when(parkingPlaceRepository.findGarageByUser(user)).thenReturn(garage);
        Mockito.when(otpRepository.exists(dto.getUserEmail(), garage.getId(), otp.getOtpKey())).thenReturn(true);

        garageService.registerVehicle(dto, otp, idUser);
        Mockito.verify(parkingRepository).save(parkingArgumentCaptor.capture());

        Parking registered = parkingArgumentCaptor.getValue();

        assertEquals(ParkingType.GARAGE, registered.getParkingType());
        assertEquals(vehicle, registered.getVehicle());
        assertEquals(user, registered.getMobileUser());
    }

    @Test
    public void testRegisterVehicleWithInvalidOTP() {
        VehicleIngressDTO vehicleIngressDTO = new VehicleIngressDTO();
        OTPDTO otpDto = new OTPDTO();
        Long garageAdminUserId = 1L;
        MobileUser user = new MobileUser();
        Garage garage = new Garage();

        when(userRepository.findUserById(1L)).thenReturn(user);
        when(parkingPlaceRepository.findGarageByUser(user)).thenReturn(garage);
        when(otpRepository.exists(vehicleIngressDTO.getUserEmail(), garageAdminUserId, otpDto.getOtpKey())).thenReturn(false);

        assertThrows(OTPNotFoundException.class, () -> {
            garageService.registerVehicle(vehicleIngressDTO, otpDto, garageAdminUserId);
        });
    }


    @Test
    public void testSendOtpSuccess() throws MessagingException {
        String mail = "abc@mail.com";
        Long idGarage = 1L;

        garageService.sendOtp(mail, idGarage);

        Mockito.verify(otpRepository).save(otpArgumentCaptor.capture());

        OTP otp = otpArgumentCaptor.getValue();

        String link = String.format("http://localhost:8080/eparking/mobile/reports?mail=%s&idGarage=%d", mail, idGarage);

        String messageWithStyles = "<div style=\"background-color: rgb(20, 20, 20); display: block;\">\n" +
                "    <div style=\"text-align: center; justify-content: center;\">\n" +
                "        <img src=\"https://i.imgur.com/P8FBUXF.png\" style=\"width: 200px; height: 230px\">\n" +
                "    </div>\n" +
                "<div style=\"width: 100%; height: 2em; background-color: #FEBC3D;\"></div>\n" +
                "<h1 style=\"text-align: center; justify-content: center; color: antiquewhite;\">Código: " + otp.getOtpKey() + "</h1>\n" +
                "<div style=\"width: 100%; height: 2em; background-color: #FEBC3D;\"></div>\n" +
                "<p style=\"text-align: center; justify-content: center; color: antiquewhite;\">Estan queriendo ingresar tu vehiculo, si no sos vos, hace la denuncia: <a href=\"" + link + "\">AQUI</a></p>\n" +
                "</div>";

        verify(emailService).sendMimeMessage(mail, "Clave de ingreso:", messageWithStyles);
    }

    @Test
    public void testSendOtpException() throws MailException {
        doNothing().when(emailService).sendSimpleMessage("email", "Clave de ingreso:", "1");
    }

    @Test
    public void testEgressVehicle() {
        Long idUser = 1L;
        VehicleIngressDTO dto = new VehicleIngressDTO();
        MobileUser user = new MobileUser();
        Parking parking = new Parking(ParkingType.GARAGE, null, null, null, Date.from(Instant.now()));
        Garage garage = new Garage("Mi Garage", 10, new Geolocation(0.0, 0.0),"", 5.0f, 0.25f, 15L);
        Vehicle vehicle = new Vehicle();
        List<Parking> parkingList = new ArrayList<>();

        configSetUpEgressVehicleClasses(dto, user, parking, garage, vehicle, parkingList);

        Mockito.when(userRepository.findUserById(idUser))
                .thenReturn(user);
        Mockito.when(vehicleRepository.findVehicleByPatent(dto.getPatent()))
                .thenReturn(vehicle);
        Mockito.when(parkingPlaceRepository.findGarageByUser(user)).thenReturn(garage);

        garageService.egressVehicle(dto.getPatent(), idUser);

        Mockito.verify(parkingRepository).save(parkingArgumentCaptor.capture());
        Parking registered = parkingArgumentCaptor.getValue();

        assertEquals(ParkingType.GARAGE, registered.getParkingType());
        assertEquals(vehicle, registered.getVehicle());
        assertEquals(user, registered.getMobileUser());
        assertFalse(garage.removeVehicle(vehicle.getPatent()));
    }

    private void configSetUpEgressVehicleClasses(VehicleIngressDTO dto, MobileUser user, Parking parking, Garage garage, Vehicle vehicle, List<Parking> parkingList) {
        dto.setPatent("ABC123");
        user.setParkings(parkingList);
        vehicle.setUser(user);
        vehicle.setPatent("ABC123");
        parking.setVehicle(vehicle);
        parking.setMobileUser(user);
        parkingList.add(parking);
        garage.addVehicle(vehicle.getPatent());
    }

    @Test
    void testGetRegisteredVehicles() {
        Long garageAdminUserId = 1L;
        MobileUser user = new MobileUser();
        Garage garage = mock(Garage.class);
        List<Vehicle> expectedVehicles = Arrays.asList(new Vehicle(), new Vehicle());

        when(userRepository.findUserById(garageAdminUserId)).thenReturn(user);
        when(parkingPlaceRepository.findGarageByUser(user)).thenReturn(garage);
        when(garage.getPatents()).thenReturn(new HashSet<>(Arrays.asList("ABC123", "XYZ789")));
        when(vehicleRepository.findVehiclesByPatents(any())).thenReturn(expectedVehicles);

        List<Vehicle> actualVehicles = garageService.getRegisteredVehicles(garageAdminUserId);

        assertEquals(expectedVehicles, actualVehicles);

        verify(userRepository).findUserById(garageAdminUserId);
        verify(parkingPlaceRepository).findGarageByUser(user);
        verify(garage).getPatents();
        verify(vehicleRepository).findVehiclesByPatents(any());
    }

    @Test
    void testGetUserByPatent() {
        String patent = "ABC123";
        Vehicle vehicle = new Vehicle();
        MobileUser user = new MobileUser();
        vehicle.setUser(user);

        when(vehicleRepository.findVehicleByPatent(patent)).thenReturn(vehicle);

        MobileUser resultUser = garageService.getUserByPatent(patent);

        assertEquals(user, resultUser);
        verify(vehicleRepository, times(1)).findVehicleByPatent(patent);
    }

    @Test
    void testGetVehicleByPatent() {
        String patent = "ABC123";
        Vehicle vehicle = new Vehicle();

        when(vehicleRepository.findVehicleByPatent(patent)).thenReturn(vehicle);

        Vehicle resultVehicle = garageService.getVehicleByPatent(patent);

        assertEquals(vehicle, resultVehicle);
        verify(vehicleRepository, times(1)).findVehicleByPatent(patent);
    }

    @Test
    void testGetGarageByAdminUserId() {
        Long adminUserId = 1L;
        MobileUser user = new MobileUser();
        Garage garage = mock(Garage.class);

        when(userRepository.findUserById(adminUserId)).thenReturn(user);

        when(parkingPlaceRepository.findGarageByUser(user)).thenReturn(garage);

        Garage resultGarage = garageService.getGarageByAdminUserId(adminUserId);

        assertEquals(garage, resultGarage);
        verify(userRepository, times(1)).findUserById(adminUserId);
        verify(parkingPlaceRepository, times(1)).findGarageByUser(user);
    }

    @Test
    void whenEstimateExit_ifVehicleHasMinorTimeThanFraction_shouldPayTheFractionFee() {
        Long adminUserId = 1L;
        float feePerHour = 15;
        float feePerFraction = 5;
        long fractionTime = 15;
        createGarageByTime(adminUserId, feePerHour, feePerFraction, fractionTime);
        Parking parking = createParkingWithTime(1);

        ParkingEgressDTO response = garageService.EstimateEgressVehicle(parking, adminUserId);

        assertEquals(feePerFraction, response.getExpendPrice());
    }

    @Test
    void whenEstimateExit_ifVehicleHasMayorTimeFraction_shouldPayCorrectFee() {
        Long adminUserId = 1L;
        float feePerHour = 15;
        float feePerFraction = 5;
        long fractionTime = 15;
        createGarageByTime(adminUserId, feePerHour, feePerFraction, fractionTime);
        Parking parking = createParkingWithTime(120);

        ParkingEgressDTO response = garageService.EstimateEgressVehicle(parking, adminUserId);

        assertEquals(2 * feePerHour, response.getExpendPrice());
    }

    @Test
    void whenEstimateExit_ifLessThanHourButHighThanFraction_shouldPayOneHour() {
        Long adminUserId = 1L;
        float feePerHour = 15;
        float feePerFraction = 5;
        long fractionTime = 15;
        createGarageByTime(adminUserId, feePerHour, feePerFraction, fractionTime);
        Parking parking = createParkingWithTime(30);

        ParkingEgressDTO response = garageService.EstimateEgressVehicle(parking, adminUserId);

        assertEquals(feePerHour, response.getExpendPrice());
    }

    private void createGarageByTime(Long adminId, float feePerHour, float feePerFraction, long fractionTime) {
        MobileUser user = new MobileUser();
        Garage garage = new Garage("Name", 20, null, "some adress", feePerHour, feePerFraction, fractionTime);

        when(userRepository.findUserById(adminId)).thenReturn(user);
        when(parkingPlaceRepository.findGarageByUser(user)).thenReturn(garage);
    }

    private Parking createParkingWithTime(long minutesToSubstract) {
        Instant arrival = Instant.now().minus(minutesToSubstract, ChronoUnit.MINUTES);

        return new Parking(
                ParkingType.STREET,
                null,
                null,
                null,
                Date.from(arrival)
        );
    }

}