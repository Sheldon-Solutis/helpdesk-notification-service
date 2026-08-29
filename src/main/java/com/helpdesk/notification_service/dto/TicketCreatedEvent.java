package com.helpdesk.notification_service.dto;

import java.time.LocalDateTime;

public record TicketCreatedEvent (
    Long ticketId,
    Long technicianId,
    Long customerId,
    String title,
    String priority,
    LocalDateTime createdAt
) {}
