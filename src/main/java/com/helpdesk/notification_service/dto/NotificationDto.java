package com.helpdesk.notification_service.dto;

import com.helpdesk.notification_service.enums.Status;
import com.helpdesk.notification_service.enums.Type;
import com.helpdesk.notification_service.messaging.event.TicketAssignedEvent;
import com.helpdesk.notification_service.messaging.event.TicketCreatedEvent;
import com.helpdesk.notification_service.messaging.event.TicketDeletedEvent;
import com.helpdesk.notification_service.messaging.event.TicketStatusChangedEvent;
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
    private Status oldTicketStatus;
    private Status newTicketStatus;

    public NotificationDto(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.ticketId = notification.getTicketId();
        this.technicianId = notification.getTechnicianId();
        this.type = notification.getType();
        this.message = notification.getMessage();
        this.oldTicketStatus = notification.getOldTicketStatus();
        this.newTicketStatus = notification.getNewTicketStatus();
    }

    public NotificationDto(TicketAssignedEvent event) {
        this.ticketId = event.ticketId();
        this.technicianId = event.technicianId();
        this.type = Type.TICKET_ASSIGNED;
        this.message = "Ticket with id: " + event.ticketId() +
                " has been assigned to technician id: " + event.technicianId();
        this.newTicketStatus = Status.OPEN;
    }

    public NotificationDto(TicketCreatedEvent event) {
        this.ticketId = event.ticketId();
        this.technicianId = event.technicianId();
        this.type = Type.TICKET_CREATED;
        this.message = "Ticket with id: " + event.ticketId() +
                " has been created with priority: " + event.priority();

        this.newTicketStatus = Status.OPEN;
    }

    public NotificationDto(TicketStatusChangedEvent event) {
        this.ticketId = event.ticketId();
        this.technicianId = event.technicianId();
        this.type = Type.TICKET_STATUS_CHANGED;
        this.message = "Ticket with id: " + event.ticketId() +
                " changed status from " + event.previousStatus() + " to " + event.newStatus();
        this.oldTicketStatus = Status.valueOf(event.previousStatus());
        this.newTicketStatus = Status.valueOf(event.newStatus());
    }

    public NotificationDto(TicketDeletedEvent event) {
        this.ticketId = event.ticketId();
        this.technicianId = event.technicianId();
        this.type = Type.TICKET_DELETED;
        this.message = "Ticket with id: " + event.ticketId() + " (\"" + event.title() + "\") has been deleted";
    }
}
