package com.helpdesk.notification_service.dto;

import com.helpdesk.notification_service.enums.Status;
import com.helpdesk.notification_service.model.Notification;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long notificationId;
    private Long ticketId;
    private Long technicianId;
    private String message;
    private Status newTicketStatus;

    public NotificationDto(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.ticketId = notification.getTicketId();
        this.technicianId = notification.getTechnicianId();
        this.message = notification.getMessage();
        this.newTicketStatus = notification.getNewTicketStatus();
    }
}
