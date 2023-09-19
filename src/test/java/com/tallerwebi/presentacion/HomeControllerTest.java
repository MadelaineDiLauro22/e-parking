package com.tallerwebi.presentacion;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    private static final String HOME_VIEW_NAME = "home";
    private HomeController homeController;
    private HttpSession sessionMock;

    @BeforeEach
    public void setUp(){
        sessionMock = new MockHttpSession();

        sessionMock.setAttribute("id", 1L);
        sessionMock.setAttribute("nickName", "carlitos");

        homeController = new HomeController(sessionMock);
    }

    @Test
    void shouldGetView() {
        ModelAndView response = homeController.getHomeRegister();

        assertEquals(HOME_VIEW_NAME, response.getViewName());
    }

    @Test
    void shouldGetViewWithId() {
        ModelAndView response = homeController.getHomeRegister();

        assertEquals(HOME_VIEW_NAME, response.getViewName());

        assertTrue(response.getModel().containsKey("id"));
        assertEquals(1L, response.getModel().get("id"));
    }

    @Test
    void shouldGetViewWithNickname() {
        ModelAndView response = homeController.getHomeRegister();

        assertEquals(HOME_VIEW_NAME, response.getViewName());

        assertTrue(response.getModel().containsKey("nickname"));
        assertEquals("carlitos", response.getModel().get("nickname"));
    }

}
