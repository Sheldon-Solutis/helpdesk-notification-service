package com.helpdesk.notification_service.model;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.messaging.event.TicketCreatedEvent;
import com.helpdesk.notification_service.enums.Status;
import com.helpdesk.notification_service.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(nullable = false)
    private Long ticketId;

    @Column(name = "technician_id")
    private Long technicianId;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_ticket_status")
    private Status oldTicketStatus;

    // TicketDeletedEvent não carrega status, então esse campo passou a
    // aceitar null (era nullable = false, quebrava a notificação de
    // exclusão de chamado).
    @Enumerated(EnumType.STRING)
    @Column(name = "new_ticket_status")
    private Status newTicketStatus;

    @Column(name = "ticket_created_at")
    private LocalDateTime ticketCreatedAt;

    @Column(name = "notification_date_time", nullable = false)
    private LocalDateTime notificationDateTime;

    public Notification(TicketCreatedEvent ticket) {
        this.ticketId = ticket.ticketId();
        this.technicianId = ticket.technicianId();
        this.newTicketStatus = Status.OPEN;
        this.ticketCreatedAt = ticket.createdAt();
    }

    public Notification(NotificationDto dto) {
        this.ticketId = dto.getTicketId();
        this.technicianId = dto.getTechnicianId();
        this.message = dto.getMessage();
        this.type = dto.getType();
        this.oldTicketStatus = dto.getOldTicketStatus();
        this.newTicketStatus = dto.getNewTicketStatus();
        this.notificationDateTime = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.notificationDateTime = LocalDateTime.now();
    }
}
