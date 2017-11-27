package ch.fhnw.edu.eaf.usermgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsermgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermgtApplication.class, args);
	}
}
