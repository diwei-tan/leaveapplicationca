package sg.edu.nus.leaveapplication.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.nus.leaveapplication.model.PublicHoliday;
import sg.edu.nus.leaveapplication.repo.PublicHolidayRepository;

import sg.edu.nus.leaveapplication.model.LeaveApplication;

public class LeaveServices {
	
	private PublicHolidayRepository publicHolidayRepo;

	@Autowired
	public void setPublicHolidayRepo(PublicHolidayRepository publicHolidayRepo) {
		this.publicHolidayRepo = publicHolidayRepo;
	}

	public LeaveServices() {
		super();
		// TODO Auto-generated constructor stub
	}

	// method to calculate
	public double calculateNumOfLeaveDays(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
		if (startDate == null || endDate == null)
			throw new Exception("start date and/or end date cannot be null.");
		if (endDate.isBefore(startDate))
			throw new Exception("start date must be before the end date");

		LocalDateTime checkDate = startDate;
		// check total days between. NOTE: each date means one day of leave. As such,
		// days should be daysBetween+1
		double days = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
		double numOfDays = 0;

		// if daysBetween is more than 14 days, than all days are counted. skip checks
		// below
		// iterate from start(using checkdate) to end date, adding days/half day that
		// are not weekends or public holidays
		while (!checkDate.isAfter(endDate)) {
			if (days > 14) {
				if (isHalfDay(checkDate, endDate)) {
					 numOfDays += 0.5;
				} else {
					numOfDays += 1;
				}
			} else {
				// if not more than 14, exclude weekends and public holidays
				if (checkDate.getDayOfWeek() != DayOfWeek.SATURDAY && checkDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
					// add nested if condition for public holidays
					//if (!isPH(checkDate)) {
						// check if is half day. If so, this is last (half) day so return num of days
						// plus 0.5.
						// if not numOfDays++
						if (isHalfDay(checkDate, endDate)) {
							numOfDays += 0.5;
						} else {
							numOfDays++;
						}
					//}
					// shift startdate by 1, continue until it is past enddate	
				}
			}
			checkDate = checkDate.plusDays(1);
		}
		// after loop, number of
		return numOfDays;
	}

	public LocalDateTime calculateEndDate(LocalDateTime startDate, double numOfDays) throws Exception {
		// numOfDays cannot be <=0. StartDate cannot be a date before today
		if (numOfDays <= 0)
			throw new Exception("number of leave days cannot be 0 or less than 0.");
		if (startDate.isBefore(LocalDateTime.now()))
			throw new Exception("leave cannot begin from a date before today.");

		LocalDateTime endDate = startDate;
		while (numOfDays != 0) {
			if (endDate.getDayOfWeek() != DayOfWeek.SATURDAY && endDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
				// if half day, return current date plus 12hrs. else, continue
				if (numOfDays == 0.5)
					return endDate.plusHours(12);
				else
					numOfDays--;
			}
			endDate = endDate.plusDays(1);
		}
		return endDate;
	}

	public boolean isHalfDay(LocalDateTime startDate, LocalDateTime endDate) {

		// for half day, startDate and endDate date is same but one time is am and
		// another pm.
		if (startDate.toLocalDate().equals(endDate.toLocalDate())
				&& (startDate.getHour() == 12 || endDate.getHour() == 12))
			return true;
		else
			return false;
	}

	// extra methods overlap to check if dates overlap
	public boolean overlaps(LeaveApplication leaveApplication1, LeaveApplication leaveApplication2) {
		// Checks if two leave application coincide
		return (leaveApplication1.getEndDate().isAfter(leaveApplication2.getStartDate())
				&& leaveApplication1.getStartDate().isBefore(leaveApplication2.getEndDate()));
	}

	// method to count num of PH days to be excluded from total count of leave dates
//	public boolean isPH(LocalDateTime checkDate) {
//		// generate list based on start/end dates
//		// extract from repo
//		if (publicHolidayRepo.findAll().isEmpty()) {
//			return false;
//		} else {
//			List<PublicHoliday> phList = publicHolidayRepo.findAll();
//			// extract only dates from repo
//			Collection<LocalDate> phDates = CollectionUtils.collect(phList,
//					TransformerUtils.invokerTransformer("getDate"));
//			// add common dates (excluding weekends) to new templist
//			for (LocalDate date : phDates) {
//				if (checkDate.toLocalDate().equals(date)) {
//					return true;
//				}
//			}
//			return false;
//		}
//	}
}
