package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;


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
}
