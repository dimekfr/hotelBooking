package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.challenge.hotelBooking.dtos.StayPeriodDTO;
import com.challenge.hotelBooking.entities.Checkin;
import com.challenge.hotelBooking.enums.StayStatus;
import com.challenge.hotelBooking.exception.ResourceNotFoundException;

public class ObjectMapperHelper {

	public static Map<String, List<StayPeriodDTO>> groupingStayPeriodByGuestDocumentNumber(List<Checkin> checkins) {
		Map<String, List<StayPeriodDTO>> stayPeriodMap = new HashMap<String, List<StayPeriodDTO>>();

		for (Checkin checkin : checkins) {
			StayPeriodDTO stayPeriod = new StayPeriodDTO();
			stayPeriod.setEffectiveFrom(checkin.getCheckinDate());
			stayPeriod.setEffectiveTo(checkin.getCheckoutDate());
			stayPeriod.setHasVehicle(checkin.getHasVehicle());

			if (stayPeriodMap.get(checkin.getGuest().getDocumentNumber()) == null) {
				stayPeriodMap.put(checkin.getGuest().getDocumentNumber(), new ArrayList<StayPeriodDTO>());
			}
			stayPeriodMap.get(checkin.getGuest().getDocumentNumber()).add(stayPeriod);

		}
		return stayPeriodMap;
	}

	public static Map<String, List<StayPeriodDTO>> filterCheckinPeriodByStatus(List<Checkin> checkins, String status) {

		Map<String, List<StayPeriodDTO>> groupedStayPeriodMap = groupingStayPeriodByGuestDocumentNumber(checkins);
		Map<String, List<StayPeriodDTO>> filteredStayPeriodMap = new HashMap<String, List<StayPeriodDTO>>();

		String stayStatusValue = status.toUpperCase();

		StayStatus stayStatus = Enum.valueOf(StayStatus.class, stayStatusValue);

		switch (stayStatus) {

		case CURRENT:

			filteredStayPeriodMap = groupedStayPeriodMap.entrySet().stream()
			.filter(a -> a.getValue().stream().anyMatch(stayPeriod -> stayPeriod.getEffectiveTo() == null))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			break;

		case PASSED:

			filteredStayPeriodMap = groupedStayPeriodMap.entrySet().stream()
			.filter(a -> a.getValue().stream().allMatch(stayPeriod -> stayPeriod.getEffectiveTo() != null))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			break;

		default:

			throw new ResourceNotFoundException("Stay Status", "status", status);
		}

		return filteredStayPeriodMap;
	}

}
