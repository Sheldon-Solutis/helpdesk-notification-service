package com.helpdesk.notification_service.controller;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping
    public List<NotificationDto> listAllNotifications() {
        return notificationService.listAll();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotification(@PathVariable Long id) {
        return notificationService.findById(id);
    }

}
