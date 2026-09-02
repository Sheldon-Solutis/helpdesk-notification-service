package com.helpdesk.notification_service.controller;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.enums.Status;
import com.helpdesk.notification_service.enums.Type;
import com.helpdesk.notification_service.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void listAllNotifications_deveRetornar200ComLista() throws Exception {
        NotificationDto dto = new NotificationDto(10L, 1L, null, Type.TICKET_CREATED,
                "Chamado #1 aberto", null, Status.OPEN);
        when(notificationService.listAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticketId").value(1));
    }

    @Test
    void getNotification_deveRetornar404QuandoNaoEncontrado() throws Exception {
        when(notificationService.findById(eq(99L)))
                .thenThrow(new EntityNotFoundException("Ticket Not Found With Id: 99"));

        mockMvc.perform(get("/api/notifications/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotification_deveRetornar200ComNotificacao() throws Exception {
        NotificationDto dto = new NotificationDto(1L, 5L, 20L, Type.TICKET_ASSIGNED,
                "Chamado #5 atribuído", null, Status.OPEN);
        when(notificationService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("TICKET_ASSIGNED"));
    }
}
