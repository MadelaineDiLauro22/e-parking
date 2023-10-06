package com.tallerwebi.presentacion;

import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationControllerDummy {

    /*private final NotificationService notificationService;

    public NotificationControllerDummy(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("send/{id}")
    public void sendNotification(@PathVariable Long id, @RequestBody NotificationRequestDTO request) {
        //notificationService.setUserNotification(request, id);
        notificationService.sendNotificationMessage();
    }*/

}
