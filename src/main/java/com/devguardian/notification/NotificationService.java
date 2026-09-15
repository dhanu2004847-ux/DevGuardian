package com.devguardian.notification;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {
    private final EmailService emailService;
    public NotificationService(EmailService emailService) { this.emailService = emailService; }

    public List<Notification> listNotifications() { return List.of(); }
    public void markAsRead(Long id) {}
}