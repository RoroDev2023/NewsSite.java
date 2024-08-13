package com.example.demo.config.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "mainTopic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}