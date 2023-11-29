package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.NotificationRestService;
import com.tallerwebi.helpers.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("notification")
public class NotificationRestController {

    private final NotificationRestService notificationRestService;
    private final NotificationService notificationService;
    private final HttpSession session;

    public NotificationRestController(NotificationRestService notificationRestService, NotificationService notificationService, HttpSession session) {
        this.notificationRestService = notificationRestService;
        this.notificationService = notificationService;
        this.session = session;
    }

    @PostMapping("seen")
    @ResponseStatus(HttpStatus.OK)
    public void seeNotifications() {
        notificationRestService.seeNotifications((Long) session.getAttribute("id"));
        notificationService.sendMessage((Long) session.getAttribute("id"));
    }

}
