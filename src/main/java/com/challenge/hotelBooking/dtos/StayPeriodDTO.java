package com.challenge.hotelBooking.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StayPeriodDTO {

	private LocalDateTime effectiveFrom;
	private LocalDateTime effectiveTo;
	Boolean hasVehicle;

}
