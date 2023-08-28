package com.challenge.hotelBooking.services.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.hotelBooking.dtos.GuestDTO;
import com.challenge.hotelBooking.entities.Guest;
import com.challenge.hotelBooking.exception.ResourceNotFoundException;
import com.challenge.hotelBooking.repositories.GuestRepository;
import com.challenge.hotelBooking.services.GuestService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Service
public class GuestServiceImp implements GuestService {

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<GuestDTO> findAll(Pageable pageable) {
		Page<Guest> guests = guestRepository.findAll(pageable);
		return new PageImpl<>(
				guests.stream().map((guest) -> (modelMapper.map(guest, GuestDTO.class))).collect(Collectors.toList()),
				pageable, guests.getSize());
	};

	@Override
	public GuestDTO createGuest(@NotNull GuestDTO guestDTO) {
		Guest guest = modelMapper.map(guestDTO, Guest.class);
		Guest savedGuest = guestRepository.save(guest);
		return modelMapper.map(savedGuest, GuestDTO.class);
	}

	@Override
	public GuestDTO updateGuest(@NotNull Long id, @NotNull GuestDTO guestDTO) {
		Guest guest = guestRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
		guest.setName(guestDTO.getName());
		guest.setDocumentNumber(guestDTO.getDocumentNumber());
		guest.setPhone(guestDTO.getPhone());
		Guest savedGuest = guestRepository.save(guest);
		return modelMapper.map(savedGuest, GuestDTO.class);
	}

	@Override
	public GuestDTO findById(@NotNull Long id) {
		Guest guest = guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
		return modelMapper.map(guest, GuestDTO.class);
	}

	@Override
	public GuestDTO findByDocumentNumber(@NotBlank String documentNumber) {
		Optional<Guest> guest = guestRepository.findByDocumentNumber(documentNumber);
		if (guest.isEmpty())
			throw new ResourceNotFoundException("Guest", "documentNumber", documentNumber);
		return modelMapper.map(guest, GuestDTO.class);
	}

	@Override
	public GuestDTO findByName(@NotBlank String name) {
		Optional<Guest> guest = guestRepository.findByName(name);
		if (guest.isEmpty())
			throw new ResourceNotFoundException("Guest", "name", name);
		return modelMapper.map(guest, GuestDTO.class);
	}

	@Override
	public GuestDTO findByPhone(@NotBlank String phone) {
		Optional<Guest> guest = guestRepository.findByPhone(phone);
		if (guest.isEmpty())
			throw new ResourceNotFoundException("Guest", "phone", phone);
		return modelMapper.map(guest, GuestDTO.class);
	}

	@Override
	public void deleteGuest(@NotNull Long id) {
		Guest guest = guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));
		guestRepository.deleteById(guest.getId());
	}

}
