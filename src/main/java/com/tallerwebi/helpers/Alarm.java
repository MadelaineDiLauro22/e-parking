package com.tallerwebi.helpers;

import com.tallerwebi.model.NotificationType;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Alarm {

    private final NotificationService notificationService;

    public Alarm(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void createAlarm() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        ZonedDateTime nextRun = now.withSecond(5);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);

        Duration duration = Duration.between(now, nextRun);
        long initialDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> notificationService.registerAndSendNotification(new NotificationRequestDTO("Alarm", "sound alarm", NotificationType.ALARM)),
                initialDelay,
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }

}
