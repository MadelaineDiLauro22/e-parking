package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorControllerTest {
    private ErrorController errorController;
    private HttpSession sessionMock;

    @BeforeEach
    public void setUp(){
        sessionMock = new MockHttpSession();

        errorController = new ErrorController(sessionMock);
    }

    @Test
    public void shouldGetErrorPage(){
        ModelAndView page = errorController.getErrorController();
        assertEquals("error", page.getViewName());
    }

}
