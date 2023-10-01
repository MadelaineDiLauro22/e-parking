package com.tallerwebi.infraestructura;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
class NotificationRepositoryImplTest {

    private static final String TITLE = "Some notification";
    private static final String MESSAGE = "some message";
    private static final Date DATE = Date.from(Instant.now());
    private MobileUser user = null;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = new MobileUser();
    }

    @Transactional
    @Rollback
    @Test
    void shouldSaveNotificationAndGetListOfOne() {
        Notification notification = createAndSaveNotification(false);

        List<Notification> notifications = notificationRepository.findAllByUser(user);

        assertEquals(1, notifications.size());
        assertEquals(notification, notifications.get(0));
    }

    @Transactional
    @Rollback
    @Test
    void shouldGetNotificationsByUserAndNotRead() {
        Notification notification = createAndSaveNotification(true);
        Notification notification2 = createAndSaveNotification(false);

        List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

        assertEquals(1, notifications.size());
        assertFalse(notifications.contains(notification));
        assertTrue(notifications.contains(notification2));
    }

    private Notification createAndSaveNotification(boolean isRead) {
        Notification notification = new Notification(TITLE, MESSAGE, DATE, user);
        if (isRead) notification.setRead(true);
        user.addNotification(notification);

        userRepository.save(user);
        notificationRepository.save(notification);

        return notification;
    }

}