package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.OTPNotFoundException;
import com.tallerwebi.helpers.EmailService;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.OTPDTO;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GarageServiceImplTest {

    private GarageService garageService;
    @Mock
    private GarageServiceImpl garageServiceImpl;
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

    /*@Test
    public void testRegisterVehicleSuccess() {
       VehicleIngressDTO vehicleIngressDTO = new VehicleIngressDTO();
        OTPDTO otpDto = new OTPDTO("1L");
        Long garageAdminUserId = 123L;
        when(otpRepository.exists(vehicleIngressDTO.getUserEmail(), garageAdminUserId, otpDto.getOtpKey())).thenReturn(true);

        garageService.addToGarage(vehicleIngressDTO, garageAdminUserId);

        verify(garageServiceImpl, times(1)).addToGarage(vehicleIngressDTO, garageAdminUserId);
    }*/

    @Test
    public void testRegisterVehicleException() throws OTPNotFoundException {
        GarageServiceImpl garageService = new GarageServiceImpl(userRepository, vehicleRepository, parkingPlaceRepository, parkingRepository, emailService, otpRepository);
        VehicleIngressDTO vehicleIngressDTO = new VehicleIngressDTO();
        OTPDTO otpDto = new OTPDTO("1L");
        Long garageAdminUserId = 123L;
        when(otpRepository.exists(vehicleIngressDTO.getUserEmail(), garageAdminUserId, otpDto.getOtpKey())).thenReturn(false);
    }

    @Test
    public void testSendOtpSuccess() {
        emailService.sendSimpleMessage("email", "Clave de ingreso:", "1");
        verify(emailService).sendSimpleMessage("email", "Clave de ingreso:", "1");
    }

    @Test
    public void testSendOtpException() throws MailException {
        doNothing().when(emailService).sendSimpleMessage("email", "Clave de ingreso:", "1");
    }

    /*@Test
    public void testEgressVehicle() {
        String vehiclePatent = "ABC123";
        Long garageAdminUserId = 1L;

        Garage garage = new Garage();
        when(garageService.getGarageByAdminUserId(garageAdminUserId)).thenReturn(garage);

        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.findVehicleByPatent(vehiclePatent)).thenReturn(vehicle);

        MobileUser user = new MobileUser();
        List<Parking> parkingList = new ArrayList<>();
        Parking latestParking = new Parking();
        parkingList.add(latestParking);
        user.setParkings(parkingList);
        vehicle.setUser(user);

        ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO(ParkingType.GARAGE, vehiclePatent, null, null, 1.0, 2.0, garage.getId());

        garageService.egressVehicle(vehiclePatent, garageAdminUserId);

        verify(parkingRepository, times(1)).save(latestParking);
    }*/
}