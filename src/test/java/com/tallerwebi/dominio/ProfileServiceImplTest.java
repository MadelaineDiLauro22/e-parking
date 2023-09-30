package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.Parking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceImplTest {

    private ProfileServiceImpl profileService;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ParkingRepository mockParkingRepository;
    @Captor
    private ArgumentCaptor<Parking> parkingCaptor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileService = new ProfileServiceImpl(mockVehicleRepository, mockUserRepository, mockParkingRepository);
    }

    
}