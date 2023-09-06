package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Usuario;
import com.tallerwebi.model.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ParkingServiceImplTest {

    private ParkingServiceImpl parkingService;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private RepositorioUsuario mockRepositorioUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parkingService = new ParkingServiceImpl(mockVehicleRepository, mockRepositorioUsuario);
    }

    @Test
    void shouldGetVehicleList(){
        Long userId = 1L;
        List<Vehiculo> cars = new ArrayList<>();
        MobileUser user = new MobileUser();

        Mockito.when(mockRepositorioUsuario.buscarUsuarioPorId(userId))
                .thenReturn(user);

        Mockito.when(mockVehicleRepository.obtenerVehiculosPorUsuario(user))
                .thenReturn(cars);

        List<Vehiculo> vehicles = parkingService.getUserCarsList(userId);

        assertEquals(cars,vehicles);
    }

    @Test
    void whenTryToGetUserCarList_IfUserNotExist_ShouldThrowException(){
        Long userId = 1L;

        Mockito.when(mockRepositorioUsuario.buscarUsuarioPorId(userId))
                .thenReturn(null);

        assertThrows(UsuarioInexistente.class,() -> parkingService.getUserCarsList(userId));
    }
}