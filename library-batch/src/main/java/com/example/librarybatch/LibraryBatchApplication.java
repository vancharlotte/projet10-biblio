package com.example.librarybatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients("com.example.librarybatch")
public class LibraryBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryBatchApplication.class, args);

	}

}
