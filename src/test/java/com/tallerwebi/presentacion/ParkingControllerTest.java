package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.model.Geolocation;
import com.tallerwebi.model.PointSale;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingPlaceResponseDTO;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingControllerTest {

    public static final String PARKING_VIEW_NAME = "parking-register";
    private ParkingController parkingController;

    @Mock
    private ParkingService mockParkingService;

    @Mock
    private HttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parkingController = new ParkingController(mockParkingService, mockHttpSession);
    }

    @Test
    void shouldGetViewWithCarListAndParkingPlacesList() {
        List<Vehicle> cars = new ArrayList<>();
        List<ParkingPlaceResponseDTO> parkingPlaces = new ArrayList<>();

        Mockito.when(mockParkingService.getUserCarsList(getUserId()))
                .thenReturn(cars);
        Mockito.when(mockParkingService.getParkingPlaces())
                .thenReturn(parkingPlaces);

        ModelAndView response = parkingController.getParkingRegister();
        List<Vehicle> responseList = (List<Vehicle>) response.getModel().get("vehicleList");
        List<Vehicle> responseListParkingPlaces = (List<Vehicle>) response.getModel().get("parkingPlaces");

        assertEquals(PARKING_VIEW_NAME, response.getViewName());
        assertEquals(cars, responseList);
        assertEquals(parkingPlaces, responseListParkingPlaces);
    }

    @Test
    void shouldRegisterParkingAndReturnToHomeWithSuccess() {
        ParkingRegisterDTO parkingRegisterDTO = getParkingRegisterDTO();

        Mockito.when(mockHttpSession.getAttribute("id"))
                .thenReturn(getUserId());

        ModelAndView response = parkingController.registerParking(parkingRegisterDTO, new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]));
        Mockito.verify(mockParkingService).registerParking(parkingRegisterDTO, getUserId());

        assertEquals("redirect:/mobile/home", response.getViewName());
        assertTrue((boolean) response.getModel().get("success"));
    }

    @Test
    void whenRegisterFail_ShouldReturnParkingViewAndError() {
        Long userId = 3L;
        ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO();

        Mockito.when(mockHttpSession.getAttribute("id"))
                .thenReturn(userId);
        Mockito.doThrow(new UserNotFoundException())
                .when(mockParkingService).registerParking(parkingRegisterDTO, userId);

        ModelAndView response = parkingController.registerParking(parkingRegisterDTO, new MockMultipartFile("vehicle_pic", new byte[0]),
                new MockMultipartFile("ticket_pic", new byte[0]));

        assertEquals("redirect:/error?errorMessage=Usuario inexistente", response.getViewName());
    }

    @Test
    void whenGetUserById_IfUserNotExists_ShouldShowMessage() {
        Long userId = 3L;

        Mockito.when(mockHttpSession.getAttribute("id"))
                .thenReturn(userId);
        Mockito.when(mockParkingService.getUserCarsList(userId))
                .thenThrow(new UserNotFoundException());

        ModelAndView response = parkingController.getParkingRegister();

        assertEquals("redirect:/error?errorMessage=Usuario+inexistente", response.getViewName());
    }

    @Test
    void whenGetVehiclesListByUserId_ifNotHaveVehicles_shouldShowMessage() {
        Long userId = 3L;

        Mockito.when(mockHttpSession.getAttribute("id"))
                .thenReturn(userId);
        Mockito.when(mockParkingService.getUserCarsList(userId))
                .thenThrow(new VehicleNotFoundException());

        ModelAndView response = parkingController.getParkingRegister();

        assertEquals("redirect:/error?errorMessage=Veh%C3%ADculo+inexistente", response.getViewName());
    }

    private Long getUserId(){
        return 1L;
    }

    private ParkingRegisterDTO getParkingRegisterDTO(){
        ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO();
        PointSale pointSale = new PointSale("point 1",new Geolocation(-23112.32,-3242432.3),"",20,20,20L);
        parkingRegisterDTO.setParkingPlaceId(1L);

        return parkingRegisterDTO;
    }
}