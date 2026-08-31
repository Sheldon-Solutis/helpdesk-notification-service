package com.helpdesk.notification_service.dto;

import com.helpdesk.notification_service.enums.Status;
import com.helpdesk.notification_service.enums.Type;
import com.helpdesk.notification_service.messaging.event.TicketAssignedEvent;
import com.helpdesk.notification_service.messaging.event.TicketCreatedEvent;
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
    private Type type;
    private String message;
    private Status newTicketStatus;

    public NotificationDto(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.ticketId = notification.getTicketId();
        this.technicianId = notification.getTechnicianId();
        this.message = notification.getMessage();
        this.newTicketStatus = notification.getNewTicketStatus();
    }

    public NotificationDto(TicketAssignedEvent event) {
        this.ticketId = event.ticketId();
        this.technicianId = event.technicianId();
        this.newTicketStatus = Status.OPEN;
    }

    public NotificationDto(TicketCreatedEvent event) {
        this.ticketId = event.ticketId();
        this.technicianId = event.technicianId();
        this.type = Type.TICKET_CREATED;
        this.message = "Ticket with id: " + event.ticketId() +
                " has been assigned by technician id: " + event.technicianId();

        this.newTicketStatus = Status.OPEN;
    }
}
