package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DaysHandler {

	public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public static long countsWeekendDays(List<LocalDate> dates) {
		Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		final long numberOfWeekDay = dates.stream().filter(d -> weekend.contains(d.getDayOfWeek())).count();
		return numberOfWeekDay;
	}

	public static long countsBusinessDays(List<LocalDate> dates) {
		Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		final long numberOfWeekDay = dates.stream().filter(d -> !weekend.contains(d.getDayOfWeek())).count();
		return numberOfWeekDay;
	}

	public static LocalDateTime getCurrentLocalDateTime() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
		return now;
	}

	public static List<LocalDate> GetAllDatesBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate) {
		LocalDate localStartDate = startDate.toLocalDate();
		LocalDate localEndDate = endDate.toLocalDate();

		return localStartDate.datesUntil(localEndDate).collect(Collectors.toList());
	}

	public static double getStayAmount(long numberOfBusinessDays, double businessDayValue, long numberOfWeekendDays,
			double weekendDayValue, double extraValue) {
		return (numberOfBusinessDays * businessDayValue) + (numberOfWeekendDays * weekendDayValue) + extraValue;
	}

	public static Boolean isExtraDays(LocalDateTime day) {
		return day.toLocalTime().isAfter(LocalTime.of(16, 30));
	}

	public static Boolean isWeekEnd(LocalDateTime day) {
		return (day.getDayOfWeek() == DayOfWeek.SUNDAY) || (day.getDayOfWeek() == DayOfWeek.SATURDAY);
	}

	public static LocalDateTime parseStringToDateTime(String dateTimeString) {
		return LocalDateTime.parse(dateTimeString, DEFAULT_DATE_FORMATTER);
	}

	public static String ConvertDateTimeToString(LocalDateTime dateTime) {
		return dateTime.format(DEFAULT_DATE_FORMATTER);
	}

}
