package ru.itis.kafka.producer.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static ru.itis.kafka.producer.KafkaProducerApplication.*;


@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@RestController
public class FilesController {
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/files")
    public ResponseEntity<String> sendFiles(@RequestParam("fileName") String fileName) {
        assert kafkaTemplate != null;

        CompletableFuture<SendResult<String, String>> fileCompletableFuture = kafkaTemplate.send(FILES_TOPIC, fileName);
        handleSendResult(FILES_TOPIC, fileName, fileCompletableFuture);
        System.out.println("here1");

        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
            CompletableFuture<SendResult<String, String>> imageCompletableFuture = kafkaTemplate.send(IMAGES_TOPIC, fileName);
            handleSendResult(IMAGES_TOPIC, fileName, imageCompletableFuture);
            System.out.println("here2");
        } else {
            CompletableFuture<SendResult<String, String>> document = kafkaTemplate.send(DOCUMENTS_TOPIC, fileName);
            handleSendResult(DOCUMENTS_TOPIC, fileName, document);
            System.out.println("here3");
        }

        return null;
    }

    private void handleSendResult(String topic, String fileName, CompletableFuture<SendResult<String, String>> completableFuture) {
        completableFuture.thenAccept(sendResult -> {
            System.out.println(topic + " message sent successfully: " + sendResult.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            System.err.println(topic + " error sending message: " + ex.getMessage());
            return null;
        });
    }
}
