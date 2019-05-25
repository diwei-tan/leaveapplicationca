package sg.edu.nus.leaveapplication.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.leaveapplication.LeaveapplicationcaApplication;
import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.util.LeaveServices;

@Controller
public class LeaveApplicationController {
	Logger LOG = LoggerFactory.getLogger(LeaveapplicationcaApplication.class);
	
	private CredentialsRepository credRepo;
	private EmployeeRepository employeeRepo;
	private LeaveRepository leaveRepo;
	
	@Autowired
	public void setLeaveRepo(LeaveRepository leaveRepo) {
		this.leaveRepo = leaveRepo;
	}
	@Autowired
	public void setCredRepo(CredentialsRepository credRepo) {
		this.credRepo = credRepo;
	}
	@Autowired
	public void setEmployeeRepo(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}


	
	@GetMapping(path="/home")
	public String leaveMainPage(Model model,Principal principal) {
		//Should anyone be able to login, directed to this page to show the leave information of themselves
		//should show approved list of leave application that are not yet past
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Credentials user = credRepo.findByUsername(name);
		model.addAttribute("user", user);
		List<LeaveApplication>leaveList = leaveRepo.findByUserId(user.getUserId());
		model.addAttribute("leaveList", leaveList);
		return "home";
	}
	
//	@RequestMapping(path="/home",method=RequestMethod.POST)
//	public String returnMainPage(Model model)
//	{
//		List<LeaveApplication>leaveList = leaveRepo.findAll();
//		model.addAttribute("leaveList", leaveList);
//		return "home";
//	}
	
	@GetMapping(path="/create{id}")
	public String loadMethod(@PathVariable("id") long id, Model model) {
		model.addAttribute("leaveApplication", new LeaveApplication());
		return "leaveform";
		
	}


	@PostMapping(path="/leaveapplication/submit{id}")
	public String processStupidForm(@PathVariable("id") long id, @ModelAttribute("form") LeaveApplication form) {
		Employee emp = employeeRepo.findById(id).orElse(null);
		LeaveServices service = new LeaveServices();
		LeaveApplication leaveApplication = form;
		try {
			leaveApplication.setNumDays(service.calculateNumOfLeaveDays(leaveApplication.getStartDate(), leaveApplication.getEndDate()));
		}
		catch(Exception e) {
			LOG.info(e.getMessage());
		}
		leaveApplication.setEmployee(emp);
		leaveRepo.save(leaveApplication);
		
		return "redirect:/home";
		
	}
	
	@RequestMapping(path="/manager/approve",method=RequestMethod.GET)
	public String approveleave(@ModelAttribute("form") LeaveApplication form,Model model) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Credentials user = credRepo.findByUsername(name);
		List<LeaveApplication> l = leaveRepo.findByManagerId(user.getUserId());		
		model.addAttribute("approve", l);
		return"approveform";
	}
	
	@RequestMapping(path="/manager/approve/{id}",method=RequestMethod.GET)
	public String acceptleave(@PathVariable(name = "id") long id,LeaveApplication leaveApplication) {
	 leaveApplication = leaveRepo.findById (id).orElse(null);		
	leaveApplication.setStatus("Approved");
	leaveRepo.save(leaveApplication);		
		return"afterform";
	}
	
	@RequestMapping(path="/manager/reject/{id}",method=RequestMethod.GET)
	public String rejectleave(@PathVariable(name = "id") long id,LeaveApplication leaveApplication) {
	 leaveApplication = leaveRepo.findById(id).orElse(null);	
	leaveApplication.setStatus("Rejected");
	leaveRepo.save(leaveApplication);		
		return"afterform";
	}
}
