package com.helpdesk.notification_service.messaging.rabbitmq;

import com.helpdesk.notification_service.dto.TicketCreatedEvent;
import com.helpdesk.notification_service.enums.Type;
import com.helpdesk.notification_service.model.Notification;
import com.helpdesk.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "ticket.queue")
    public void receiveTicket(TicketCreatedEvent ticket) {
        Notification notification = new Notification(ticket);
        notification.setType(Type.TICKET_CREATED);
        notification.setMessage("Ticket with id: "
                + ticket.ticketId() +
                ", Created Successfully, in date: "
                + ticket.createdAt().format(
                        DateTimeFormatter.ofPattern("dd/MM/AAAA")));

        notificationRepository.save(notification);
    }


}
