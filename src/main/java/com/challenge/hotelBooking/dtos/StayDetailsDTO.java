package com.challenge.hotelBooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StayDetailsDTO {

	private GuestDTO guest;
	private Double totalStaysAmount;
	private Double lastStayAmount;

}
