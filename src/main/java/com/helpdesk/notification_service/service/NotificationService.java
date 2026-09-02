package com.helpdesk.notification_service.service;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.messaging.event.TicketAssignedEvent;
import com.helpdesk.notification_service.messaging.event.TicketCreatedEvent;
import com.helpdesk.notification_service.messaging.event.TicketDeletedEvent;
import com.helpdesk.notification_service.messaging.event.TicketStatusChangedEvent;
import com.helpdesk.notification_service.model.Notification;
import com.helpdesk.notification_service.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationDto> listAll(){
        return notificationRepository.findAll()
                .stream()
                .map(NotificationDto::new)
                .toList();
    }

    public NotificationDto findById(Long id){
        return new NotificationDto(
                notificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Ticket Not Found With Id: " + id)));
    }

    public void handleTicketAssigned(TicketAssignedEvent event) {
        Notification notification = new Notification(new NotificationDto(event));
        notificationRepository.save(notification);
    }

    public void handleTicketCreated(TicketCreatedEvent event) {
        Notification notification = new Notification(new NotificationDto(event));
        notificationRepository.save(notification);
    }

    // Handler estava vazio (nada era persistido pra esse evento).
    public void handleTicketStatusChanged(TicketStatusChangedEvent event) {
        Notification notification = new Notification(new NotificationDto(event));
        notificationRepository.save(notification);
    }

    // Idem: handler vazio. Precisou de Type.TICKET_DELETED (adicionado ao
    // enum) e de newTicketStatus deixar de ser nullable=false, já que um
    // TicketDeletedEvent não carrega status.
    public void handleTicketDeleted(TicketDeletedEvent event) {
        Notification notification = new Notification(new NotificationDto(event));
        notificationRepository.save(notification);
    }
}
