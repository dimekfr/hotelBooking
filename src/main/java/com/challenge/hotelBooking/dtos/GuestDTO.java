package com.challenge.hotelBooking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class GuestDTO {

	private Long id;

	@NotBlank(message = "Invalid Name: Empty name")
	@NotNull(message = "Invalid Name: Null name")
	@Size(min = 2, message = "Name should have at least 2 characters")
	private String name;

	@NotBlank(message = "Document Number should not be empty")
	@NotNull(message = "Document Number should not be Null")
	@Size(min = 8, max = 8, message = "Document Number should have exactely 8 characters")
	private String documentNumber;

	@NotBlank(message = "Phone should not be empty")
	@NotNull(message = "Phone should not be Null")
	@Size(min = 9, max=14, message = "Phone should have at least 9 characters")
	private String phone;
}
