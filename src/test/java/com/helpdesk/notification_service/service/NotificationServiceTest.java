package com.helpdesk.notification_service.service;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.enums.Status;
import com.helpdesk.notification_service.enums.Type;
import com.helpdesk.notification_service.messaging.event.TicketAssignedEvent;
import com.helpdesk.notification_service.messaging.event.TicketCreatedEvent;
import com.helpdesk.notification_service.messaging.event.TicketDeletedEvent;
import com.helpdesk.notification_service.messaging.event.TicketStatusChangedEvent;
import com.helpdesk.notification_service.model.Notification;
import com.helpdesk.notification_service.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes unitários dos 4 handlers de evento do notification-service. O
 * caso de handleTicketAssigned cobre especificamente um bug que existia
 * aqui: o "type" da notificação não era preenchido (violava a constraint
 * nullable=false na coluna) — o teste trava esse comportamento.
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void handleTicketCreated_devePersistirNotificacaoComTipoCorreto() {
        TicketCreatedEvent event = new TicketCreatedEvent(
                1L, null, 10L, "Monitor não liga", "HIGH", LocalDateTime.now());

        notificationService.handleTicketCreated(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();

        assertThat(saved.getTicketId()).isEqualTo(1L);
        assertThat(saved.getType()).isEqualTo(Type.TICKET_CREATED);
        assertThat(saved.getNewTicketStatus()).isEqualTo(Status.OPEN);
    }

    @Test
    void handleTicketAssigned_deveSetarTypeParaNaoViolarConstraintDoBanco() {
        TicketAssignedEvent event = new TicketAssignedEvent(2L, 20L, "VPN não conecta", LocalDateTime.now());

        notificationService.handleTicketAssigned(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();

        assertThat(saved.getType()).isEqualTo(Type.TICKET_ASSIGNED);
        assertThat(saved.getTechnicianId()).isEqualTo(20L);
    }

    @Test
    void handleTicketStatusChanged_devePersistirStatusAntigoENovo() {
        TicketStatusChangedEvent event = new TicketStatusChangedEvent(
                3L, 10L, 20L, "OPEN", "IN_PROGRESS", LocalDateTime.now());

        notificationService.handleTicketStatusChanged(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();

        assertThat(saved.getOldTicketStatus()).isEqualTo(Status.OPEN);
        assertThat(saved.getNewTicketStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(saved.getType()).isEqualTo(Type.TICKET_STATUS_CHANGED);
    }

    @Test
    void handleTicketDeleted_devePersistirComTypeDeleted() {
        TicketDeletedEvent event = new TicketDeletedEvent(4L, 10L, 20L, "Erro no sistema", LocalDateTime.now());

        notificationService.handleTicketDeleted(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        assertThat(captor.getValue().getType()).isEqualTo(Type.TICKET_DELETED);
    }

    @Test
    void findById_deveLancarNotFoundQuandoNaoExiste() {
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.findById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findById_deveMapearNotificationParaDto() {
        Notification notification = Notification.builder()
                .notificationId(1L).ticketId(5L).type(Type.TICKET_CREATED)
                .message("Chamado #5 aberto").newTicketStatus(Status.OPEN)
                .build();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        NotificationDto dto = notificationService.findById(1L);

        assertThat(dto.getTicketId()).isEqualTo(5L);
        assertThat(dto.getMessage()).isEqualTo("Chamado #5 aberto");
    }
}
