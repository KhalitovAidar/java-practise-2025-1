package ru.itis.kafka.consumer.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("file-consumer")
@Component
public class FileConsumer {

    @KafkaListener(topics = "files", groupId = "filesGroupId")
    private void fileConsume(@Payload String file) {
        System.out.printf("Get file %s%n", file);
    }
}
