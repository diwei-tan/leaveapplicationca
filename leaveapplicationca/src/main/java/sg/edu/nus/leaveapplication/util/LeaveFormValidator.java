package sg.edu.nus.leaveapplication.util;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.repo.PHRepository;

@Component
public class LeaveFormValidator implements Validator {
	
	private LeaveRepository leaveRepo;
	private PHRepository phrepo;
	@Autowired
	public void setLeaveRepo(LeaveRepository leaveRepo) {
		this.leaveRepo = leaveRepo;
	}
	@Autowired
	public void setPhrepo(PHRepository phrepo) {
		this.phrepo=phrepo;
	}
	
	@Autowired
	LeaveServices leaveServices;

	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {

		LeaveApplication request = (LeaveApplication) o;
		// set number of days to use for validation

		// validation on reason
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "NotEmpty");

		if (!errors.hasErrors()) {
			// startDate validation
			// time must either be 00:00 or 12:00
			// cannot be public holiday or weekends
			boolean doPhaseTwo = true;
			boolean doPhaseThree = true;

			if ((request.getStartDate().getHour()==0||request.getStartDate().getHour()==12)
					&& request.getStartDate().getMinute()==0) {
				
			} else {
				errors.rejectValue("startDate", "NotAMPM");
				doPhaseThree=false;
				doPhaseThree=false;
			}
			
			if (request.getStartDate().getDayOfWeek() == DayOfWeek.SATURDAY
					|| request.getStartDate().getDayOfWeek() == DayOfWeek.SUNDAY
					|| leaveServices.isPH(request.getStartDate())) {
				errors.rejectValue("startDate", "IsPHOrWeekend");
				doPhaseTwo = false;
				doPhaseThree = false;
			}
			// endDate validation
			// time must be either 00:00 or 12:00
			// cannot be PH or weekends
			// cannot come before start date
			// end date must be within the same year as start date
			if (request.getEndDate().isBefore(request.getStartDate())) {
				errors.rejectValue("endDate", "EndDate.Before");
				doPhaseTwo = false;
				doPhaseThree = false;
			}
			if (request.getEndDate().getYear() != request.getStartDate().getYear()) {
				errors.rejectValue("endDate", "EndDate.WithinYear");
				doPhaseTwo = false;
				doPhaseThree = false;
			}
			if ((request.getStartDate().getHour()==0||request.getStartDate().getHour()==12)
					&& request.getStartDate().getMinute()==0) {
				
			} else {
				errors.rejectValue("endDate", "NotAMPM");
				doPhaseThree=false;
				doPhaseThree=false;
			}
			if (request.getEndDate().getDayOfWeek() == DayOfWeek.SATURDAY
					|| request.getEndDate().getDayOfWeek() == DayOfWeek.SUNDAY
					|| leaveServices.isPH(request.getEndDate())) {
				errors.rejectValue("endDate", "IsPHOrWeekend");
				doPhaseTwo = false;
				doPhaseThree = false;
			}

			// if above pass, then check whether leave application date clashes with other
			// things.
			// first, warn if a half day used when not compensation leave
			if ((request.getStartDate().getHour() == 12 || request.getEndDate().getHour() == 12)) {
				if (!(request.getStartDate().getHour() == 12 && request.getEndDate().getHour() == 12) &&
						!request.getType().equals("Compensationleave")) {
					errors.rejectValue("startDate", "HalfDayButNotCompLeave");
					doPhaseTwo = false;
					doPhaseThree = false;
				}
			}

			// check if leave overlaps with any applied/approved leave within the year. Only
			// if above no error.
			if (doPhaseTwo) {
				List<LeaveApplication> leaveList = leaveRepo.findByUserId(request.getEmployee().getId());
				List<LeaveApplication> checkList = leaveList.stream()
						.filter(x -> x.getStatus().equals("Applied") || x.getStatus().equals("Approved"))
						.collect(Collectors.toList());
				// if leave request overlaps with any leave, add error
				for (LeaveApplication leave : checkList) {
					if (leaveServices.overlaps(request, leave)) {
						errors.rejectValue("startDate", "LeaveApplication.Overlap");
						doPhaseThree = false;
						break;
					}
				}
				// phase three nest in phase two, and only if phase two has no errors than run
				// this
				if (doPhaseThree) {
					// set number of days
//					try {
//						request.setNumDays(
//								leaveServices.calculateNumOfLeaveDays(request.getStartDate(), request.getEndDate()));
//					} catch (Exception e) {
//						System.out.println(e.getMessage());
//					}
					// check if enough leave to apply for this leave application
					if (request.getType().equals("Annualleave")) {
						// filter all annual leave
						checkList = leaveList.stream().filter(x -> x.getType().equals("Annualleave"))
								.collect(Collectors.toList());
						System.out.print("$$$$$$$$$$"+leaveList);
						// filter all leave of the application year
						checkList = checkList.stream()
								.filter(x -> x.getStartDate().getYear() == request.getStartDate().getYear())
								.collect(Collectors.toList());
						// sum all days
						long days = 0;
						for (LeaveApplication leave : checkList) {
							days += leave.getNumDays();
						}
						if (request.getEmployee().getLeaveEntitled() < (days + request.getNumDays())) {
							errors.rejectValue("startDate", "AnnualLeave.Insufficient");
						}
					} else if (request.getType().equals("Medicalleave")) {
						// filter all annual leave
						checkList = leaveList.stream().filter(x -> x.getType().equals("Medicalleave"))
								.collect(Collectors.toList());
						// filter all leave of the application year
						checkList = checkList.stream()
								.filter(x -> x.getStartDate().getYear() == request.getStartDate().getYear())
								.collect(Collectors.toList());
						// sum all days
						long days = 0;
						for (LeaveApplication leave : checkList) {
							days += leave.getNumDays();
						}
						if ((days + request.getNumDays()) > 60) {
							errors.rejectValue("startDate", "MedicalLeave.Insufficient");
						}
					} else if (request.getType().equals("Compensationleave")) {
						if (request.getNumDays() > (request.getEmployee().getCompensationhours() / 4 * 0.5)) {
							errors.rejectValue("startDate", "Compensation.Insufficient");
						}
					}
				}
			}
		}
	}
}
