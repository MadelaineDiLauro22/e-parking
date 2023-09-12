package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingControllerTest {

    public static final String PARKING_VIEW_NAME = "parking-register";
    private ParkingController parkingController;

    @Mock
    private ParkingService mockParkingService;

    @Mock
    private HttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parkingController = new ParkingController(mockParkingService, mockHttpSession);
    }

    @Test
    void shouldGetViewWithCarList() {
        Long userId = 1L;
        Double lat = 1.0;
        Double lng = 1.0;
        List<Vehicle> cars = new ArrayList<>();

        Mockito.when(mockParkingService.getUserCarsList(userId))
                .thenReturn(cars);

        ModelAndView response = parkingController.getParkingRegister();
        List<Vehicle> responseList = (List<Vehicle>) response.getModel().get("vehicleList");

        assertEquals(PARKING_VIEW_NAME, response.getViewName());
        assertEquals(cars, responseList);
    }

    @Test
    void shouldRegisterParkingAndReturnToHomeWithSuccess(){

        ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO();

        Mockito.when(mockParkingService.registerParking(parkingRegisterDTO)).thenReturn(true);

        ModelAndView response = parkingController.registerParking(parkingRegisterDTO);

        assertEquals("redirect:/home", response.getViewName());
        assertTrue((boolean)response.getModel().get("success"));
    }

    @Test
    void whenRegisterFail_ShouldReturnParkingViewAndError(){

        ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO();

        Mockito.when(mockParkingService.registerParking(parkingRegisterDTO)).thenReturn(false);

        ModelAndView response = parkingController.registerParking(parkingRegisterDTO);

        assertEquals("parking-register", response.getViewName());
        assertFalse((boolean)response.getModel().get("success"));
    }

}