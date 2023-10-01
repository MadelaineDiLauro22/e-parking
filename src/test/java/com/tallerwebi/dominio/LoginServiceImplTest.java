package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceImplTest {

    private LoginService LoginService;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        LoginService = new LoginServiceImpl(mockUserRepository);
    }

}
