package com.challenge.hotelBooking;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "com.challenge.hotelBooking.controllers", "com.challenge.hotelBooking.services" })
@EntityScan("com.challenge.hotelBooking.entities")
@EnableJpaRepositories("com.challenge.hotelBooking.repositories")
public class HotelBookingApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingApplication.class, args);
	}

}
