package com.tallerwebi.presentacion;

import com.tallerwebi.helpers.NotificationService;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationControllerDummy {

    private final NotificationService notificationService;

    public NotificationControllerDummy(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("send")
    public void sendNotification(@RequestBody NotificationRequestDTO request) {
        notificationService.registerNotification(request);
        notificationService.sendMessage();
    }

}
