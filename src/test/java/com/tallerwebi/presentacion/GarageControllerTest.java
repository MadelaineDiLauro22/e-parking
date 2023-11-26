package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageServiceImpl;
import com.tallerwebi.model.Garage;
import com.tallerwebi.model.Geolocation;
import com.tallerwebi.presentacion.dto.OTPDTO;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void shouldGetEnterVehicle(){
        ModelAndView page = garageController.enterVehicle();
        assertEquals ("garage-enter-vehicle", page.getViewName());
    }

    @Test
    public void shouldRegisterVehicle_ThenReturnToEnterVehicle(){
        VehicleIngressDTO vehicleIngressDTO = getVehicleIngressDTO();
        OTPDTO otpdto = new OTPDTO();
        Mockito.when(sessionMock.getAttribute("id"))
                .thenReturn(getUserId());

        ModelAndView response = garageController.validationRegisterVehicle(vehicleIngressDTO, otpdto);
        Mockito.verify(garageService).registerExistingVehicleInSystem(vehicleIngressDTO, otpdto, getUserId());

        assertEquals("redirect:/web/admin/", response.getViewName());
        assertTrue((boolean) response.getModel().get("success"));
    }
    private Long getUserId(){
        return 1L;
    }

    private Garage getGarage(){
        return new Garage("Garage",10,new Geolocation(21879.21312,2187313.3),"",10,14,2L);
    }

    private VehicleIngressDTO getVehicleIngressDTO(){
        VehicleIngressDTO vehicleIngressDTO = new VehicleIngressDTO();
        vehicleIngressDTO.setPatent("ABC123");
        vehicleIngressDTO.setBrand("BMW");
        vehicleIngressDTO.setModel("2012");
        vehicleIngressDTO.setColor("Black");
        vehicleIngressDTO.setUserName("admnin");
        vehicleIngressDTO.setUserEmail("test@unlam.edu.ar");
        return vehicleIngressDTO;
    }
}
