package ru.itis.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = "payments", groupId = "notification-group")
    public void notifyUser(String paymentStatus) {
        System.out.println("Sending notification: " + paymentStatus);
    }
}