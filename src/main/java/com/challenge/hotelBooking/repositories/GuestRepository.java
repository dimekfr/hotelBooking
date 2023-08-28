package com.challenge.hotelBooking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.hotelBooking.entities.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

	Optional<Guest> findByDocumentNumber(String documentNumber);

	Optional<Guest> findByName(String guestName);

	Optional<Guest> findByPhone(String phone);

}
