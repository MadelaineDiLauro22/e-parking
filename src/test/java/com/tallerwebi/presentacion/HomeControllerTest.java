package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    private static final String HOME_VIEW_NAME = "home";
    private HomeController homeController;
    private HttpSession sessionMock;

    @BeforeEach
    public void setUp(){
        sessionMock = new MockHttpSession();

        homeController = new HomeController(sessionMock);
    }

    @Test
    public void shouldGetHomePage(){
        ModelAndView page = homeController.getHomeRegister();
        assertEquals ("home", page.getViewName());
    }

}
