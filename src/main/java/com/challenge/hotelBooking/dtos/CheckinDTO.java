package com.challenge.hotelBooking.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckinDTO {

	private Long id;

	private GuestDTO guest;

	@NotNull(message = "Checkin date should not be Null")
	private LocalDateTime checkinDate;

	private LocalDateTime checkoutDate;

	@NotNull(message = "hasVehicle should not be Null")
	private Boolean hasVehicle;

}
