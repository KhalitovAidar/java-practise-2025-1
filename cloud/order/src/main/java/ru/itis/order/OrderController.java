package ru.itis.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "orders";

    @PostMapping("/orders")
    public String createOrder(@RequestBody String order) {
        kafkaTemplate.send(TOPIC, order);
        return "Order created: " + order;
    }
}