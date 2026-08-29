package com.helpdesk.notification_service.dto;

import java.time.LocalDateTime;

public record TicketStatusChangedEvent(
        Long ticketId,
        Long customerId,
        Long technicianId,
        String previousStatus,
        String newStatus,
        LocalDateTime updatedAt
) {
}
