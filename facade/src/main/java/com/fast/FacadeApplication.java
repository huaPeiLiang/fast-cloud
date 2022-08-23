package com.fast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FacadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacadeApplication.class, args);
	}

}
