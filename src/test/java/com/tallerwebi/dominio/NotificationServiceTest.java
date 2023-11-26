package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NotificationServiceException;
import com.tallerwebi.helpers.Mapper;
import com.tallerwebi.helpers.NotificationService;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.NotificationType;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private NotificationService notificationService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private NotificationRepository mockNotificationRepository;
    @Captor
    private ArgumentCaptor<Notification> notificationCaptor;
    private static final WebSocketSession WS_SESSION = mock(WebSocketSession.class);
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(mockNotificationRepository, mockUserRepository, new Mapper());

        //notificationService.setWebSocketSession(WS_SESSION);
        //notificationService.setUserId(USER_ID);
        when(WS_SESSION.isOpen()).thenReturn(true);
        notificationService.setWebsocketSession(USER_ID, WS_SESSION);
    }

    @Test
    void shouldSendMessageToUser() throws IOException {
        MobileUser user = createAndPersistUser();
        createAndAddNotifications(user);

        notificationService.sendMessage(USER_ID);
        verify(WS_SESSION).sendMessage(any());
    }

    @Test
    void shouldRegisterNewNotificationToUser_andSaveItWithEqualsTitleMessage() {
        String title = "Hi!";
        String message = "This is a notification";
        NotificationRequestDTO request = createNotificationRequest(title, message);
        MobileUser user = createAndPersistUser();

        notificationService.registerNotification(request);
        Notification notification = verifyRepositoriesAndExtractNotification(user);

        assertEquals(title, notification.getTitle());
        assertEquals(message, notification.getMessage());
    }

    @Test
    void whenRegisterNotification_ifUserNotExist_shouldThrowException() {
        String title = "Hi!";
        String message = "This is a notification";
        NotificationRequestDTO request = createNotificationRequest(title, message);

        when(mockUserRepository.findUserById(USER_ID))
                .thenReturn(null);

        assertThrows(NotificationServiceException.class, () -> notificationService.registerNotification(request));
    }

    private Notification verifyRepositoriesAndExtractNotification(MobileUser user) {
        verify(mockUserRepository).save(user);
        verify(mockNotificationRepository).save(notificationCaptor.capture());

        return notificationCaptor.getValue();
    }

    private void createAndAddNotifications(MobileUser user) {
        Notification notification = new Notification();
        user.addNotification(notification);

        when(mockNotificationRepository.findAllByUserAndNotRead(user)).thenReturn(List.of(notification));
    }

    private MobileUser createAndPersistUser() {
        MobileUser user = new MobileUser();

        when(mockUserRepository.findUserById(USER_ID))
                .thenReturn(user);

        return user;
    }

    private NotificationRequestDTO createNotificationRequest(String title, String message) {
        return new NotificationRequestDTO(title, message, NotificationType.MESSAGE, USER_ID);
    }

}