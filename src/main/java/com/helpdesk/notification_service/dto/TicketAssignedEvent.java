package com.helpdesk.notification_service.dto;

import java.time.LocalDateTime;

public record TicketAssignedEvent(
   Long ticketId,
   Long technicianId,
   String title,
   LocalDateTime updatedAt
) {}
