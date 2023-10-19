package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    private static final String HOME_VIEW_NAME = "home";
    private HomeController homeController;
    @Mock
    private HttpSession sessionMock;
    @Mock
    private ParkingServiceImpl parkingService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        homeController = new HomeController(sessionMock, parkingService);
    }

    @Test
    public void shouldGetHomePage(){
        ModelAndView page = homeController.getHomeRegister();
        assertEquals ("home", page.getViewName());
    }

}
