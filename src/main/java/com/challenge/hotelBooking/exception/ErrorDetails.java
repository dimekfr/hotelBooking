package com.challenge.hotelBooking.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {

	private LocalDateTime timestamp;
	private String message;
	private String description;

}
