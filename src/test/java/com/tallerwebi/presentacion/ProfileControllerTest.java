package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class ProfileControllerTest {

    @Mock
    private ProfileService mockProfileService;
    @Mock
    private ProfileController profileController;
    @Mock
    private ProfileService profileService;
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
        profileController.getNotifications();
        ModelAndView response = profileController.getNotifications();
        assertEquals("redirect:/error", response.getViewName());
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
        assertEquals("redirect:/error", modelAndView.getViewName());
    }

}

