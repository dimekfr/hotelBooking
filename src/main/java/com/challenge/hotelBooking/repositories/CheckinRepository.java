package com.challenge.hotelBooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.hotelBooking.entities.Checkin;

public interface CheckinRepository extends JpaRepository <Checkin, Long> {
	
	List<Checkin> findByGuestDocumentNumber(String documentNumber);
	
	List<Checkin> findByGuestName(String name);
	
	List<Checkin> findByGuestPhone(String phone);

}
