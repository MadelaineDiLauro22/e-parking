package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testCannotGetNotifications() throws UserNotFoundException {
        when(mockHttpSession.getAttribute("id")).thenReturn(null);
        profileController.getNotifications();
    }
}
