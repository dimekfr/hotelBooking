package com.challenge.hotelBooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private String resourceName;
	private String fieldName;
	private long id;
	private String fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, long id) {
		super(String.format("%s with %s = '%s' not found", resourceName, fieldName, id));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.id = id;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s with %s = '%s' not found", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
