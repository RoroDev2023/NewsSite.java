package com.example.demo.config.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Service for producing Kafka messages.
 */
@Service
public class KafkaProducer {

    /**
     * Logger for logging Kafka messages.
     */
    private static final Logger LOGGER = Logger.getLogger(
            KafkaProducer.class.getName());

    /**
     * KafkaTemplate for sending messages.
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Constructor for KafkaProducer.
     *
     * @param kafkaTemplateParam the KafkaTemplate for sending messages
     */
    public KafkaProducer(
            final KafkaTemplate<String, String> kafkaTemplateParam) {
        this.kafkaTemplate = kafkaTemplateParam;
    }

    /**
     * Sends a message to the Kafka topic.
     *
     * @param message the message to be sent
     */
    public void sendMessage(final String message) {
        LOGGER.info(String.format("Message sent: %s", message));
        kafkaTemplate.send("mainTopic", message);
    }
}


