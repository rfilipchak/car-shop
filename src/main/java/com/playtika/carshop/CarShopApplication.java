package com.playtika.carshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CarShopApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarShopApplication.class, args);
	}

//	@Bean
//	AuthorService authorService() {
//		return Feign.builder()
//				.decoder(new StringDecoder())
//				.target(AuthorService.class, "https://localhost:8080");
//	}

}
