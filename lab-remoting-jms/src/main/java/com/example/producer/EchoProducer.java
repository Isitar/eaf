package com.example.producer;

import javax.jms.Message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class EchoProducer {

	public static void main(String[] args) {
		SpringApplication.run(EchoProducer.class, args);
	}

	@Value("${queue.echo}")
	String queue;

	@Bean
	CommandLineRunner sendMessage(JmsTemplate jmsTemplate) {
		return args -> {
			Message m = jmsTemplate.sendAndReceive(queue, session -> session.createTextMessage("hello world"));
			System.out.println(m.getBody(String.class));
		};
	}
}