package com.helpdesk.notification_service.model;

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

    @Column(name = "old_ticket_status")
    private String oldTicketStatus;

    @Column(name = "new_ticket_status", nullable = false)
    private String newTicketStatus;

    @Column(name = "ticket_created_at", nullable = false)
    private LocalDateTime ticketCreatedAt;

    @Column(name = "notification_date_time", nullable = false)
    private LocalDateTime notificationDateTime;

    @PrePersist
    public void prePersist() {
        this.notificationDateTime = LocalDateTime.now();
    }
}
