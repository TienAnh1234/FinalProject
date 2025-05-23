package com.example.Comp1640;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Comp1640Application {

	public static void main(String[] args) {
		SpringApplication.run(Comp1640Application.class, args);
	}

}
