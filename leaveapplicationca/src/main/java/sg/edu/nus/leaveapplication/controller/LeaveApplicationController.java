package sg.edu.nus.leaveapplication.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.leaveapplication.LeaveapplicationcaApplication;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.util.LeaveServices;

@Controller
public class LeaveApplicationController {
	Logger LOG = LoggerFactory.getLogger(LeaveapplicationcaApplication.class);
	private LeaveRepository leaveRepo;
	
	@Autowired
	public void setLeaveRepo(LeaveRepository leaveRepo) {
		this.leaveRepo=leaveRepo;
	}
	
	@GetMapping(path="/home")
	public String leaveMainPage(Model model) {
		//Should anyone be able to login, directed to this page to show the leave information of themselves
		//should show approved list of leave application that are not yet past
		List<LeaveApplication>leaveList = leaveRepo.findAll();
		model.addAttribute("leaveList", leaveList);
		return "home";
	}
	
	@RequestMapping(path="/home",method=RequestMethod.POST)
	public String returnMainPage(Model model)
	{
		List<LeaveApplication>leaveList = leaveRepo.findAll();
		model.addAttribute("leaveList", leaveList);
		return "home";
	}
	
	@GetMapping(path="/create")
	public String loadMethod(Model model) {
		model.addAttribute("leaveApplication", new LeaveApplication());
		return "leaveform";
		
	}
	

	@PostMapping(path="/leaveapplication/submit")
	public String processStupidForm(@ModelAttribute("form") LeaveApplication form) {
		LeaveServices service = new LeaveServices();
		LeaveApplication leaveApplication = form;
		try {
			leaveApplication.setNumDays(service.calculateNumOfLeaveDays(leaveApplication.getStartDate(), leaveApplication.getEndDate()));
		}
		catch(Exception e) {
			LOG.info(e.getMessage());
		}		
		leaveRepo.save(leaveApplication);
		
		return "redirect:/home";
		
	}
}
