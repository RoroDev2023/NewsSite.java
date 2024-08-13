package com.example.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Main entry point for the Spring Boot application.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoApplication {

	/**
	 * Number of partitions for the Kafka topic.
	 */
	private static final int PARTITIONS = 10;

	/**
	 * Number of replicas for the Kafka topic.
	 */
	private static final int REPLICAS = 1;

	/**
	 * The main method used to launch the Spring Boot application.
	 *
	 * @param args the command-line arguments (should not be {@code null})
	 */
	public static void main(final String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * Creates a Kafka topic with the specified partitions and replicas.
	 *
	 * @return the created Kafka topic
	 */
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("topic1")
				.partitions(PARTITIONS)
				.replicas(REPLICAS)
				.build();
	}

	/**
	 * Listener method for processing messages
	 * 		from the specified Kafka topic.
	 *
	 * @param message the received message
	 */
	@KafkaListener(id = "myId", topics = "topic1")
	public void listen(final String message) {
		System.out.println(message);
	}
}





