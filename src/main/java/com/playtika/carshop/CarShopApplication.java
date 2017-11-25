package com.playtika.carshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {
//		JmxAutoConfiguration.class,
//		EndpointMBeanExportAutoConfiguration.class,
//		MetricExportAutoConfiguration.class,
//		MultipartAutoConfiguration.class,
//		SpringApplicationAdminJmxAutoConfiguration.class})

public class CarShopApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarShopApplication.class, args);
	}
}
