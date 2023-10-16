package com.tallerwebi.presentacion;

import com.tallerwebi.helpers.Alarm;
import com.tallerwebi.helpers.NotificationService;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationControllerDummy {

    private final NotificationService notificationService;
    private final Alarm alarm;

    public NotificationControllerDummy(NotificationService notificationService, Alarm alarm) {
        this.notificationService = notificationService;
        this.alarm = alarm;
    }

    @PostMapping("send")
    public void sendNotification(@RequestBody NotificationRequestDTO request) {
        notificationService.registerNotification(request);
        notificationService.sendMessage();
    }

    @PostMapping("/alarm")
    public void createAlarm() {
        alarm.createAlarm();
    }

}
