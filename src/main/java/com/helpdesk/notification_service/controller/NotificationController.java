package com.helpdesk.notification_service.controller;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Histórico de notificações geradas a partir dos eventos de chamados")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>> listAllNotifications() {
        return ResponseEntity.ok().body(notificationService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable Long id) {
        return ResponseEntity.ok().body(notificationService.findById(id));
    }

}
