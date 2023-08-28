package com.challenge.hotelBooking.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.hotelBooking.dtos.CheckinDTO;
import com.challenge.hotelBooking.dtos.StayDetailsDTO;
import com.challenge.hotelBooking.services.CheckinService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CheckinController {

	@Autowired
	private CheckinService checkinService;

	@GetMapping("/checkins")
	public ResponseEntity<Page<CheckinDTO>> gelAllCheckin(
			@SortDefault(value = "guest.name", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(checkinService.findAll(pageable));
	}

	@GetMapping("/checkin/{id}")
	public ResponseEntity<CheckinDTO> getCheckinById(@PathVariable Long id) {
		return ResponseEntity.ok(checkinService.findByid(id));
	}

	@PostMapping("/guest/{guestId}/checkin")
	public CheckinDTO createChecking(@PathVariable(value = "guestId") Long guestId,
			@Valid @RequestBody CheckinDTO checkinDTO) {
		return checkinService.createCheckin(guestId, checkinDTO);
	}

	@DeleteMapping("/checkin/{id}")
	public ResponseEntity<String> deleteCheckin(@PathVariable Long id) {
		checkinService.deleteCheckin(id);
		return new ResponseEntity<>("Checkin id = " + id + " deleted successfully.", HttpStatus.OK);
	}

	@PutMapping("/guest/{checkinId}/checkin")
	public ResponseEntity<CheckinDTO> updateCheckin(@PathVariable(value = "checkinId") Long checkinId,
			@RequestBody CheckinDTO checkinDTO) {
		return ResponseEntity.ok(checkinService.updateCheckin(checkinId, checkinDTO));
	}

	@GetMapping("guestName/{name}/checkin")
	public ResponseEntity<List<CheckinDTO>> getAllCheckinByGuestName(@PathVariable(value = "name") String name) {
		return ResponseEntity.ok(checkinService.findByName(name));
	}

	@GetMapping("guestDocumentNumber/{documentNumber}/checkin")
	public ResponseEntity<List<CheckinDTO>> getAllCheckinByGuestDocumentNumber(
			@PathVariable(value = "documentNumber") String documentNumber) {
		return ResponseEntity.ok(checkinService.findByDocumentNumber(documentNumber));
	}

	@GetMapping("guestPhone/{phone}/checkin")
	public ResponseEntity<List<CheckinDTO>> getAllCheckinByGuestPhone(@PathVariable(value = "phone") String phone) {
		return ResponseEntity.ok(checkinService.findByPhone(phone));
	}
	
   //the end point below receives as request parameter the checkinStatus witch should be current or passed. 
   //Passing passed as request parameter, the end point will return all guests name who are not anymore in the hotel, their total and last stay amount
   //Passing current, the end point will return all guests name who are in the hotel, their total and last stay amount based on current date and time.
   //otherwise, it will throw an error message.
	
	@GetMapping("/guest/checkinsValues")
	public ResponseEntity<List<StayDetailsDTO>> getGuestStaysValue(
			@RequestParam(defaultValue = "current") String checkinStatus) {
		return ResponseEntity.ok(checkinService.getCheckinValue(checkinStatus));
	}

}
