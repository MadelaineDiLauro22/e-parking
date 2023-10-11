package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserAlreadyExistException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.UserRole;
import com.tallerwebi.presentacion.dto.LoginDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceImplTest {

    private LoginService LoginService;

    private LoginDataDTO LoginDataDTO;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        LoginService = new LoginServiceImpl(mockUserRepository);
    }

    @Test
    public void testRegisterUser_UserNotFound() {
        when(mockUserRepository.findUserByMailAndPassword("", "")).thenReturn(null);

        LoginDataDTO request = new LoginDataDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("test");
        request.setRol(UserRole.USER);
        request.setName("New User");
        request.setNickName("newuser");

        assertDoesNotThrow(() -> LoginService.registerUser(request));

        verify(mockUserRepository, times(1)).save(argThat(user ->
                user.getEmail().equals(request.getEmail()) &&
                        user.getPassword().equals(request.getPassword()) &&
                        user.getRol().equals(request.getRol()) &&
                        user.getName().equals(request.getName()) &&
                        user.getNickName().equals(request.getNickName())
        ));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws UserAlreadyExistException {
        when(mockUserRepository.findUserByMailAndPassword("test@gmail.com", "test")).thenReturn(new MobileUser());

        LoginDataDTO request = new LoginDataDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("test");
        request.setRol(UserRole.USER);
        request.setName("New User");
        request.setNickName("newuser");

        assertThrows(UserAlreadyExistException.class, () -> LoginService.registerUser(request));

        verify(mockUserRepository, never()).save(any());
    }

    @Test
    public void testSearchUser_UserFound() {
        String email = "test@gmail.com";
        String password = "test";
        MobileUser expectedUser = new MobileUser(email, password, UserRole.USER, "Test User", "testuser");

        when(mockUserRepository.findUserByMailAndPassword(email, password)).thenReturn(expectedUser);

        MobileUser result = (MobileUser) LoginService.searchUser(email, password);

        assertNotNull(result);

        assertEquals(expectedUser, result);
    }

    @Test
    public void testSearchUser_UserNotFound() {
        String email = "test@gmail.com";
        String password = "test";

        when(mockUserRepository.findUserByMailAndPassword(email, password)).thenReturn(null);

        MobileUser result = (MobileUser) LoginService.searchUser(email, password);

        assertNull(result);
    }



}
