package ru.itis.kafka.consumer.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("document-consumer")
@Component
public class DocumentConsumer {

    @KafkaListener(topics = "documents", groupId = "documentsGroupId")
    private void documentConsume(@Payload String document) {
        System.out.printf("Get document: %s%n", document);
    }
}
