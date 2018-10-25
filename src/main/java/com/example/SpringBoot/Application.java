package com.example.SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.SpringBoot"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}