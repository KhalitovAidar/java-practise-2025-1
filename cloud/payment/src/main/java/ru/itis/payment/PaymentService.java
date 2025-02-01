package ru.itis.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "orders", groupId = "payment-group")
    public void processOrder(String order) {
        System.out.println("Processing payment for order: " + order);
        // Simulate payment processing
        String paymentStatus = "Payment processed for order: " + order;
        kafkaTemplate.send("payments", paymentStatus);
    }
}