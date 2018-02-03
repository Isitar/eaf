package com.example.consumer;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@SpringBootApplication
public class EchoServer {

	public static void main(String[] args) {
		SpringApplication.run(EchoServer.class, args);
	}

	@Value("${queue.echo}")
	private String queue;

	@JmsListener(destination = "${queue.echo}")
	public String echoMessage(String m) {
		return m + System.currentTimeMillis();
	}
}