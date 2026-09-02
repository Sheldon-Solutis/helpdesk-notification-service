package com.helpdesk.notification_service.messaging.rabbitmq;

import com.helpdesk.notification_service.config.RabbitMQConfig;
import com.helpdesk.notification_service.messaging.event.*;
import com.helpdesk.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConfig.TICKET_QUEUE)
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

    @RabbitHandler
    public void handle(TicketDeletedEvent event) {
        notificationService.handleTicketDeleted(event);
    }
}
