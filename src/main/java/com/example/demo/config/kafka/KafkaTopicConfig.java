package com.example.demo.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for Kafka topics.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a new Kafka topic named 'mainTopic'.
     *
     * @return the configured Kafka topic
     */
    @Bean
    public NewTopic mainTopic() {
        return TopicBuilder.name("mainTopic")
                .build();
    }
}
