package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageServiceImpl;
import com.tallerwebi.dominio.ParkingServiceImpl;
import com.tallerwebi.model.Garage;
import com.tallerwebi.model.Geolocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarageControllerTest {
    private GarageController garageController;
    @Mock
    private HttpSession sessionMock;
    @Mock
    private GarageServiceImpl garageService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        garageController = new GarageController(garageService,sessionMock);
    }

    @Test
    public void shouldGetGarageHomePage(){
        Mockito.when(sessionMock.getAttribute("id"))
                .thenReturn(getUserId());
        Mockito.when(garageService.getGarageByAdminUserId((Long)sessionMock.getAttribute("id")))
                        .thenReturn(getGarage());

        ModelAndView page = garageController.getHomeGarage();
        assertEquals ("home-garage", page.getViewName());
    }
    private Long getUserId(){
        return 1L;
    }

    private Garage getGarage(){
        return new Garage("Garage",10,new Geolocation(21879.21312,2187313.3),10,14,2L);
    }
}
