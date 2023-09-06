package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.model.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingControllerTest {

    public static final String PARKING_VIEW_NAME = "parking-register";
    private ParkingController parkingController;

    @Mock
    private ParkingService mockParkingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parkingController = new ParkingController(mockParkingService);
    }

    @Test
    void shouldGetViewWithCarList() {
        Long userId = 1L;
        List<Vehiculo> cars = new ArrayList<>();

        Mockito.when(mockParkingService.getUserCarsList(userId))
                .thenReturn(cars);

        ModelAndView response = parkingController.getParkingRegister(userId);
        List<Vehiculo> responseList = (List<Vehiculo>) response.getModel().get("vehicleList");

        assertEquals(PARKING_VIEW_NAME, response.getViewName());
        assertEquals(cars, responseList);
    }

}