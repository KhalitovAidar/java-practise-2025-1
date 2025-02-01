package ru.itis.kafka.consumer.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("image-consumer")
@Component
public class ImageConsumer {

    @KafkaListener(topics = "image", groupId = "imagesGroupId")
    private void imageConsume(@Payload String image) {
        System.out.printf("Get image %s%n", image);
    }
}
