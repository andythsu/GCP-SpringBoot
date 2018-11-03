package com.example.SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@SpringBootApplication(scanBasePackages = {"com.example.SpringBoot", "org.github.andythsu.GCP.Services"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}