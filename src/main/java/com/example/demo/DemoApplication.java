package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Main entry point for the Spring Boot application.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoApplication {
	/**
	* The main method used to launch the Spring Boot application.
	* @param args the command-line arguments (should not be {@code null})
	*/
	public static void main(final String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}



