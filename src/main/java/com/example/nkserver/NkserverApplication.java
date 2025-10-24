package com.example.nkserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NkserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NkserverApplication.class, args);
	}

}
