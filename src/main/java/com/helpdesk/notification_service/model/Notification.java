package com.helpdesk.notification_service.model;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "new_ticket_status", nullable = false)
    private Status newTicketStatus;

    @Column(name = "ticket_created_at", nullable = false)
    private LocalDateTime ticketCreatedAt;

    @Column(name = "notification_date_time", nullable = false)
    private LocalDateTime notificationDateTime;

    public Notification(TicketCreatedEvent ticket) {
        this.ticketId = ticket.ticketId();
        this.technicianId = ticket.technicianId();
        this.newTicketStatus = Status.OPEN;
        this.ticketCreatedAt = ticket.createdAt();
    }

    @PrePersist
    public void prePersist() {
        this.notificationDateTime = LocalDateTime.now();
    }
}
