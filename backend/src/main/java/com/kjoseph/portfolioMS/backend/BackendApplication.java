package com.kjoseph.portfolioMS.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.out.print(System.getProperty("java.version"));
		System.out.println("durwa");
		SpringApplication.run(BackendApplication.class, args);
	}

}
