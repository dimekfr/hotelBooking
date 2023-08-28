package com.challenge.hotelBooking.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.hotelBooking.dtos.CheckinDTO;
import com.challenge.hotelBooking.dtos.GuestDTO;
import com.challenge.hotelBooking.dtos.StayDetailsDTO;
import com.challenge.hotelBooking.dtos.StayPeriodDTO;
import com.challenge.hotelBooking.entities.Checkin;
import com.challenge.hotelBooking.entities.Guest;
import com.challenge.hotelBooking.exception.ResourceNotFoundException;
import com.challenge.hotelBooking.repositories.CheckinRepository;
import com.challenge.hotelBooking.repositories.GuestRepository;
import com.challenge.hotelBooking.services.CheckinService;

import jakarta.validation.constraints.NotNull;
import util.DaysHandler;
import util.ObjectMapperHelper;

@Service
public class CheckinServiceImp implements CheckinService {

	@Autowired
	private CheckinRepository checkinRepository;

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<CheckinDTO> findAll(Pageable pageable) {
		Page<Checkin> checkins = checkinRepository.findAll(pageable);
		return new PageImpl<>(checkins.stream().map((checkin) -> (modelMapper.map(checkin, CheckinDTO.class)))
				.collect(Collectors.toList()), pageable, checkins.getSize());
	}

	public CheckinDTO findByid(long id) {
		Checkin checkin = checkinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Checkin", "id", id));
		return modelMapper.map(checkin, CheckinDTO.class);
	}

	public CheckinDTO createCheckin(@NotNull Long guestId, @NotNull CheckinDTO checkinDTO) {

		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest", "guestId", guestId));;
		List<Checkin> checkins = checkinRepository.findByGuestPhone(guest.getPhone());
		for(Checkin checkin: checkins) {
			if((checkin.getCheckoutDate().equals(null)) || checkin.getCheckoutDate().isAfter(checkinDTO.getCheckinDate()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exist an open Checkin or overlaps period");
		}
		
		if( checkinDTO.getCheckoutDate().isBefore(checkinDTO.getCheckinDate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checkout date can not be befor checkin date");
		}
		
		checkinDTO.setGuest(modelMapper.map(guest, GuestDTO.class));
		Checkin checkin = modelMapper.map(checkinDTO, Checkin.class);
		Checkin savedCheckin = checkinRepository.save(checkin);
		return modelMapper.map(savedCheckin, CheckinDTO.class);
	}

	public CheckinDTO updateCheckin(@NotNull Long checkinId, @NotNull CheckinDTO checkinDTO) {

		Checkin checkin = checkinRepository.findById(checkinId)
				.orElseThrow(() -> new ResourceNotFoundException("Checkin", "checkinId", checkinId));
		checkin.setCheckinDate(checkinDTO.getCheckinDate());
		checkin.setCheckoutDate(checkinDTO.getCheckoutDate());
		checkin.setHasVehicle(checkinDTO.getHasVehicle());
		Checkin savedCheckin = checkinRepository.save(checkin);
		return modelMapper.map(savedCheckin, CheckinDTO.class);
	}

	public void deleteCheckin(@NotNull Long id) {
		Checkin checkin = checkinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Checkin", "id", id));
		checkinRepository.deleteById(checkin.getId());
	}

	public List<CheckinDTO> findByDocumentNumber(String documentNumber) {
		List<Checkin> checkins = checkinRepository.findByGuestDocumentNumber(documentNumber);

		if (checkins.isEmpty())
			throw new ResourceNotFoundException("Guest", "documentNumber", documentNumber);

		return checkins.stream().map((checkin) -> (modelMapper.map(checkin, CheckinDTO.class)))
				.collect(Collectors.toList());
	}

	public List<CheckinDTO> findByName(String name) {
		List<Checkin> checkins = checkinRepository.findByGuestName(name);

		if (checkins.isEmpty())
			throw new ResourceNotFoundException("Guest", "name", name);

		return checkins.stream().map((checkin) -> (modelMapper.map(checkin, CheckinDTO.class)))
				.collect(Collectors.toList());
	}

	public List<CheckinDTO> findByPhone(String phone) {
		List<Checkin> checkins = checkinRepository.findByGuestPhone(phone);
		if (checkins.isEmpty())
			throw new ResourceNotFoundException("Guest", "phone", phone);
		return checkins.stream().map((checkin) -> (modelMapper.map(checkin, CheckinDTO.class)))
				.collect(Collectors.toList());
	}

	public List<StayDetailsDTO> getCheckinValue(String stayStatus) {

		List<Checkin> checkins = checkinRepository.findAll();
		List<StayDetailsDTO> staysDetailsList = new ArrayList<StayDetailsDTO>();

		Map<String, List<StayPeriodDTO>> stayPeriodMap = ObjectMapperHelper.filterCheckinPeriodByStatus(checkins,
				stayStatus);

		stayPeriodMap.forEach((documentNumber, stayPeriodDtoList) -> { // stayPeriodDtoList must be not null

			double businessDayValue = 120.00;
			double weekendDayValue = 150.00;
			double extraValue = 0.0;
			double sumStaysAmount = 0.0;
			double lastSatyAmount = 0.0;
			long numberOfBusinessDays = 0;
			long numberOfWeekendDays = 0;

			StayDetailsDTO stayDetailsDTO = new StayDetailsDTO();
			stayDetailsDTO
					.setGuest(modelMapper.map(guestRepository.findByDocumentNumber(documentNumber), GuestDTO.class));

			List<StayPeriodDTO> sortedStayPeriodDtoList = stayPeriodDtoList.stream()
					.sorted((o2, o1) -> o1.getEffectiveFrom().compareTo(o2.getEffectiveFrom()))
					.collect(Collectors.toList());

			for (StayPeriodDTO stayPeriodDTO : sortedStayPeriodDtoList) {

				if (stayPeriodDTO.getHasVehicle() == true) {
					businessDayValue = 135.00;
					weekendDayValue = 170.00;
				}

				if (stayPeriodDTO.getEffectiveTo() == null) {
					stayPeriodDTO.setEffectiveTo(DaysHandler.getCurrentLocalDateTime());
				}

				if (DaysHandler.isExtraDays(stayPeriodDTO.getEffectiveTo())) {

					if (DaysHandler.isWeekEnd(stayPeriodDTO.getEffectiveTo())) {
						extraValue = weekendDayValue * 2;
					} else {
						extraValue = businessDayValue * 2;
					}
				} else {

					if (DaysHandler.isWeekEnd(stayPeriodDTO.getEffectiveTo())) {
						numberOfWeekendDays = 1;
					} else {
						numberOfBusinessDays = 1;
					}
				}

				List<LocalDate> dateList = DaysHandler.GetAllDatesBetweenTwoDates(stayPeriodDTO.getEffectiveFrom(),
						stayPeriodDTO.getEffectiveTo());
				numberOfBusinessDays = numberOfBusinessDays + DaysHandler.countsBusinessDays(dateList);
				numberOfWeekendDays = numberOfWeekendDays + DaysHandler.countsWeekendDays(dateList);

				if (sortedStayPeriodDtoList.get(0).equals(stayPeriodDTO)) {
					sumStaysAmount = sumStaysAmount + DaysHandler.getStayAmount(numberOfBusinessDays, businessDayValue,
							numberOfWeekendDays, weekendDayValue, extraValue);
					lastSatyAmount = DaysHandler.getStayAmount(numberOfBusinessDays, businessDayValue,
							numberOfWeekendDays, weekendDayValue, extraValue);
				} else {
					sumStaysAmount = sumStaysAmount + DaysHandler.getStayAmount(numberOfBusinessDays, businessDayValue,
							numberOfWeekendDays, weekendDayValue, extraValue);
				}

				numberOfWeekendDays = 0;
				numberOfBusinessDays = 0;
				extraValue = 0;

			}
			stayDetailsDTO.setTotalStaysAmount(sumStaysAmount);
			stayDetailsDTO.setLastStayAmount(lastSatyAmount);
			staysDetailsList.add(stayDetailsDTO);

			sumStaysAmount = 0.0;

		});

		return staysDetailsList;
	}

}
