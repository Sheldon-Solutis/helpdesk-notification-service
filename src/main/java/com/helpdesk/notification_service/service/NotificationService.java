package com.helpdesk.notification_service.service;

import com.helpdesk.notification_service.dto.NotificationDto;
import com.helpdesk.notification_service.model.Notification;
import com.helpdesk.notification_service.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

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

}
