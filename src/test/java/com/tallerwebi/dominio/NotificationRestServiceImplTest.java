package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationRestServiceImplTest {

    private NotificationRestService notificationRestService;

    @Mock
    private NotificationRepository mockNotificationRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Captor
    private ArgumentCaptor<Notification> notificationCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notificationRestService = new NotificationRestServiceImpl(mockNotificationRepository, mockUserRepository);
    }

    @Test
    void shouldSeeNotification() {
        Long userId = 1L;
        MobileUser user = createUser(userId, true);
        createNotification(user);

        notificationRestService.seeNotifications(userId);
        verify(mockNotificationRepository).save(notificationCaptor.capture());

        Notification notification = notificationCaptor.getValue();

        assertTrue(notification.isRead());
    }

    @Test
    void whenTryToSeeNotification_ifUserNotExist_shouldThrowException() {
        Long userId = 1L;
        createUser(userId, false);

        assertThrows(UserNotFoundException.class, () -> notificationRestService.seeNotifications(userId));
    }

    private MobileUser createUser(Long userId, boolean persisted) {
        MobileUser user = new MobileUser();

        if(persisted) when(mockUserRepository.findUserById(userId))
                .thenReturn(user);
        else when(mockUserRepository.findUserById(userId))
                .thenReturn(null);

        return user;
    }

    private void createNotification(MobileUser user) {
        Notification notification = new Notification("title", "message", Date.from(Instant.now()), user);

        user.addNotification(notification);
    }

}