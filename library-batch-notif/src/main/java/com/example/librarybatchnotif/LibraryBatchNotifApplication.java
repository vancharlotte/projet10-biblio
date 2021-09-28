package com.example.librarybatchnotif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients("com.example.librarybatchnotif")
public class LibraryBatchNotifApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryBatchNotifApplication.class, args);

	}

}
