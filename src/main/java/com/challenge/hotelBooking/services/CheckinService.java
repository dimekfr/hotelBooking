package com.challenge.hotelBooking.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.challenge.hotelBooking.dtos.CheckinDTO;
import com.challenge.hotelBooking.dtos.StayDetailsDTO;

public interface CheckinService {

	Page<CheckinDTO> findAll(Pageable pageable);

	CheckinDTO findByid(long id);

	CheckinDTO createCheckin(Long guestId, CheckinDTO checkinDTO);

	CheckinDTO updateCheckin(Long checkinId, CheckinDTO checkinDTO);

	void deleteCheckin(Long id);

	List<CheckinDTO> findByDocumentNumber(String documentNumber);

	List<CheckinDTO> findByName(String name);

	List<CheckinDTO> findByPhone(String phone);

	List<StayDetailsDTO> getCheckinValue(String stayStatus);

}
