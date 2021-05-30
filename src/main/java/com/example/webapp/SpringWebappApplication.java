package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.webapp.repository")
@EntityScan("com.example.webapp.model")
@SpringBootApplication
public class SpringWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebappApplication.class, args);
	}

}
