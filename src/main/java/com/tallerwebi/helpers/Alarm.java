package com.tallerwebi.helpers;

import com.tallerwebi.model.NotificationType;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class Alarm {

    private final NotificationService notificationService;

    public Alarm(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Async
    public void createAlarm(ZonedDateTime nextRun) throws InterruptedException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).withSecond(0).withNano(0);

        while (now.compareTo(nextRun) < 0) {
            Thread.sleep(60000);
            now = ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        }

       notificationService.registerAndSendNotification(new NotificationRequestDTO("Alarma", "Este es un recordatorio para mover el auto", NotificationType.ALARM));
    }

}
