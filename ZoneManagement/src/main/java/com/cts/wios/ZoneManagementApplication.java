package com.cts.wios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZoneManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoneManagementApplication.class, args);
	}

}
