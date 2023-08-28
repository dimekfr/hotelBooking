package com.challenge.hotelBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.hotelBooking.dtos.GuestDTO;
import com.challenge.hotelBooking.services.GuestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class GuestController {

	@Autowired
	public GuestService guestService;

	@PostMapping("/guest")
	public GuestDTO createGuest(@Valid @RequestBody GuestDTO guestDTO) {
		return guestService.createGuest(guestDTO);
	}

	@GetMapping("/guests")
	public ResponseEntity<Page<GuestDTO>> getAllGuest(
			@SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(guestService.findAll(pageable));
	}

	@GetMapping("/guest/{id}")
	public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long id) {
		return ResponseEntity.ok(guestService.findById(id));
	}

	@PutMapping("/guest/{id}")
	public GuestDTO updateGuest(@PathVariable Long id, @Valid @RequestBody GuestDTO guestDTO) {
		return guestService.updateGuest(id, guestDTO);
	}

	@DeleteMapping("/guest/{id}")
	public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
		guestService.deleteGuest(id);
		return new ResponseEntity<>("Guest with id = " + id + " deleted successfully.", HttpStatus.OK);
	}

	@GetMapping("/guestByName/{name}")
	public GuestDTO getGuestByName(@PathVariable String name) {
		return guestService.findByName(name);
	}

	@GetMapping("/guestByDocumentNumber/{documentNumber}")
	public GuestDTO getGuestByDocumentNumber(@PathVariable String documentNumber) {
		return guestService.findByDocumentNumber(documentNumber);
	}

	@GetMapping("/guestByPhone/{phone}")
	public GuestDTO getGuestByPhone(@PathVariable String phone) {
		return guestService.findByPhone(phone);
	}

}
