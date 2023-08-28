package com.challenge.hotelBooking.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.challenge.hotelBooking.dtos.GuestDTO;

public interface GuestService {

	Page<GuestDTO> findAll(Pageable pageable);

	GuestDTO createGuest(GuestDTO guestDTO);

	GuestDTO updateGuest(Long id, GuestDTO guestDTO);

	GuestDTO findById(Long id);

	GuestDTO findByDocumentNumber(String documentNumber);

	GuestDTO findByName(String name);

	GuestDTO findByPhone(String phone);

	void deleteGuest(Long id);

}
