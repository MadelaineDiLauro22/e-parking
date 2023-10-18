package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRestServiceImplTest {

    private NotificationRestService notificationRestService;

    @Mock
    private NotificationRepository mockNotificationRepository;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notificationRestService = new NotificationRestServiceImpl(mockNotificationRepository, mockUserRepository);
    }



}