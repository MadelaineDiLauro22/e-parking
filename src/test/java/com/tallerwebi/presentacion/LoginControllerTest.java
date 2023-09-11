package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.LoginService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.UserRole;
import com.tallerwebi.presentacion.dto.LoginDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

	private LoginController loginController;
	private MobileUser usuarioMock;
	private LoginDataDTO loginDataDTOMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private LoginService loginServiceMock;


	@BeforeEach
	public void init(){
		loginDataDTOMock = new LoginDataDTO("dami@unlam.com", "123");
		usuarioMock = mock(MobileUser.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		loginServiceMock = mock(LoginService.class);
		loginController = new LoginController(loginServiceMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(loginServiceMock.searchUser(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = loginController.validarLogin(loginDataDTOMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("rol", UserRole.ADMIN);
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion

		MobileUser usuarioEncontradoMock = mock(MobileUser.class);

		when(usuarioEncontradoMock.getRol()).thenReturn(UserRole.ADMIN);

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(loginServiceMock.searchUser(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

		// ejecucion
		ModelAndView modelAndView = loginController.validarLogin(loginDataDTOMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		verify(sessionMock, times(1)).setAttribute("rol", usuarioEncontradoMock.getRol());
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UserNotFoundException {

		// ejecucion
		ModelAndView modelAndView = loginController.registrarme(loginDataDTOMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(loginServiceMock, times(1)).registerUser(loginDataDTOMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UserNotFoundException {
		// preparacion
		doThrow(UserNotFoundException.class).when(loginServiceMock).registerUser(loginDataDTOMock);

		// ejecucion
		ModelAndView modelAndView = loginController.registrarme(loginDataDTOMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("new-user"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UserNotFoundException {
		// preparacion
		doThrow(RuntimeException.class).when(loginServiceMock).registerUser(loginDataDTOMock);

		// ejecucion
		ModelAndView modelAndView = loginController.registrarme(loginDataDTOMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("new-user"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}
}
