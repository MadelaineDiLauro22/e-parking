package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.dominio.excepcion.ParkingNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.dom4j.rule.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class ProfileControllerTest {

    @Mock
    private ProfileService mockProfileService;
    @Mock
    private ProfileController profileController;
    @Mock
    private HttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileController = new ProfileController(mockProfileService, mockHttpSession);
    }

    @Test
    void testGetNotificationsSuccess() {
        List<Notification> notifications = new ArrayList<>();
        when(mockProfileService.getAllNotificationsByMobileUser(anyLong())).thenReturn(notifications);
        ModelAndView model = profileController.getNotifications();
        assertEquals("notifications-view", model.getViewName());
        List<Notification> resultNotifications = (List<Notification>) model.getModel().get("notifications");
        assertEquals(notifications, resultNotifications);
    }

    @Test
    void testCannotGetNotifications() {
        when(mockHttpSession.getAttribute("id")).thenThrow(UserNotFoundException.class);
        ModelAndView response = profileController.getNotifications();
        assertEquals("redirect:/error?errorMessage=null", response.getViewName());
    }

    @Test
    void testGetProfileViewSuccess(){
        when(mockHttpSession.getAttribute("id")).thenReturn(123L);

        Set<Vehicle> simulatedVehicles = new HashSet<>();
        List<Parking> simulatedParkings = new ArrayList<>();

        ProfileResponseDTO simulatedResponse = new ProfileResponseDTO(simulatedVehicles, simulatedParkings);

        when(mockProfileService.getVehiclesAndParkingsByMobileUser(123L)).thenReturn(simulatedResponse);

        ModelAndView modelAndView = profileController.getProfileView();

        assertEquals("profile", modelAndView.getViewName());

        ModelMap model = modelAndView.getModelMap();
        assertTrue(model.containsAttribute("vehicles"));
        assertTrue(model.containsAttribute("parkings"));
    }

    @Test
    void testCannotGetProfileView(){
        when(mockProfileService.getVehiclesAndParkingsByMobileUser(anyLong())).thenThrow(UserNotFoundException.class);
        when(mockHttpSession.getAttribute("id")).thenReturn(456L);
        ModelAndView modelAndView = profileController.getProfileView();
        assertEquals("redirect:/error?errorMessage=null", modelAndView.getViewName());
    }

    @Test
    void testGetParkingDetail(){
        Parking parking = new Parking(ParkingType.GARAGE,new byte[8],new byte[8],new Geolocation(21312.231,12312.23),new Date());

        Mockito.when(mockHttpSession.getAttribute("id"))
                .thenReturn(1L);
        when(mockProfileService.getParkingById(anyLong(),anyLong()))
                .thenReturn(parking);

        ModelAndView response = profileController.getParkingDetail(anyLong());

        assertEquals("parking_detail-view", response.getViewName());
    }

    @Test
    void testCannotGetParkingDetailForNotParkingFound(){
        Parking parking = new Parking();
        when(mockHttpSession.getAttribute("id")).thenReturn(1L);
        when(mockProfileService.getParkingById(anyLong(),anyLong())).thenThrow(ParkingNotFoundException.class);

        ModelAndView response = profileController.getParkingDetail(anyLong());

        assertEquals("redirect:/error?errorMessage=null", response.getViewName());
    }

    @Test
    void testCannotGetParkingDetailForNotUserFound(){
        Parking parking = new Parking();
        when(mockHttpSession.getAttribute("id")).thenReturn(1L);
        when(mockProfileService.getParkingById(anyLong(),anyLong())).thenThrow(UserNotFoundException.class);

        ModelAndView response = profileController.getParkingDetail(anyLong());

        assertEquals("redirect:/error?errorMessage=null", response.getViewName());
    }

    @Test
    void shouldGetRegisterVehicleView(){
        ModelAndView page = profileController.getRegisterVehicleView();
        assertEquals("vehicle-register", page.getViewName());
    }

    @Test
    void shouldRegisterVehicle_ThenReturnToProfileViewWithSuccess(){
        VehicleRegisterDTO vehicleRegisterDTO = new VehicleRegisterDTO("ABC123", "BMW", "2023", "Red");
        Mockito.when(mockHttpSession.getAttribute("id")).thenReturn(getUserId());

        ModelAndView page = profileController.registerVehicle(vehicleRegisterDTO);
        Mockito.verify(mockProfileService).registerVehicle(vehicleRegisterDTO, getUserId());

        assertEquals("redirect:/mobile/profile", page.getViewName());
        assertTrue((boolean) page.getModel().get("success"));
    }

    private Long getUserId(){
        return 1L;
    }
}

