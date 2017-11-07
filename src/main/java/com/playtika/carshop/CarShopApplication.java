package com.playtika.carshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointMBeanExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricExportAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		JmxAutoConfiguration.class,
		EndpointMBeanExportAutoConfiguration.class,
		MetricExportAutoConfiguration.class,
		MultipartAutoConfiguration.class,
		SpringApplicationAdminJmxAutoConfiguration.class,
		ValidationAutoConfiguration.class,
		JacksonAutoConfiguration.class,
		ErrorMvcAutoConfiguration.class})
public class CarShopApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarShopApplication.class, args);
	}
}
