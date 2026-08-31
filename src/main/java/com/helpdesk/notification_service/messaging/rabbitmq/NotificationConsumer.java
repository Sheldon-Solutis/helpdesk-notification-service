package com.helpdesk.notification_service.messaging.rabbitmq;

import com.helpdesk.notification_service.messaging.event.TicketAssignedEvent;
import com.helpdesk.notification_service.messaging.event.TicketCreatedEvent;
import com.helpdesk.notification_service.messaging.event.TicketStatusChangedEvent;
import com.helpdesk.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitHandler
    public void handle(TicketCreatedEvent event) {
        notificationService.handleTicketCreated(event);
    }

    @RabbitHandler
    public void handle(TicketAssignedEvent event) {
        notificationService.handleTicketAssigned(event);
    }

    @RabbitHandler
    public void handle(TicketStatusChangedEvent event) {
        notificationService.handleTicketStatusChanged(event);
    }

}
