package sg.edu.nus.leaveapplication.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveType;
import sg.edu.nus.leaveapplication.model.PublicHoliday;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;
import sg.edu.nus.leaveapplication.repo.LeaveTypeRepository;
import sg.edu.nus.leaveapplication.repo.PHRepository;
import sg.edu.nus.leaveapplication.util.SecurityService;
import sg.edu.nus.leaveapplication.util.UserService;
import sg.edu.nus.leaveapplication.util.UserValidator;
@Controller

public class AdminController {
	
	private  EmployeeRepository employeeRepo;
	private CredentialsRepository credRepo;
	private LeaveTypeRepository leaveTypeRepo;
	
	@Autowired
	public void setLeaveTypeRepo(LeaveTypeRepository leaveTypeRepo) {
		this.leaveTypeRepo=leaveTypeRepo;
	}

	private PHRepository phRepo;

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	public void setPhRepo(PHRepository phRepo) {
		this.phRepo = phRepo;
	}


	@Autowired
	public void setCredRepo(CredentialsRepository credRepo) {
		this.credRepo = credRepo;
	}


	@Autowired
	public void setEmployeeRepo(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	
	
	
    @GetMapping("/adduser")
    public String registration(Model model) {
        model.addAttribute("userForm", new Credentials());
	    List<Employee> u = employeeRepo.findManagers();
		model.addAttribute("managers", u);
        return "registration";
    }

    


	@PostMapping("/adduser")
    public String addRegistration(@ModelAttribute("userForm") Credentials userForm, BindingResult bindingResult) {
    	userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        return "redirect:/adminhome";
    }

	
	@RequestMapping(path="/adminhome" ,method = RequestMethod.GET)
	public String listmethod(Model model) {		
		model.addAttribute("users", credRepo.findAll());
		return "adminhome";
	
	}
	
	@GetMapping("/edit{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Credentials user = credRepo.findByUserId(id);
	    List<Employee> u = employeeRepo.findManagers(); 
		model.addAttribute("managers", u);
	    model.addAttribute("user", user);
	    return "edituser";
	}
	
	@PostMapping("/update{id}")
	public String updateUser(@ModelAttribute("updateUser") Credentials updateUser, @PathVariable("id") long id, @Valid Credentials user, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        user.setUserId(id);
	        return "edituser";
	    } else {
	    	Credentials oldUser = credRepo.findById(id)
	    			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    	oldUser.getEmployee().setCompensationhours(updateUser.getEmployee().getCompensationhours());
	    	oldUser.getEmployee().setContact(updateUser.getEmployee().getContact());
	    	oldUser.getEmployee().setDesignation(updateUser.getEmployee().getDesignation());
	    	oldUser.getEmployee().setEmail(updateUser.getEmployee().getEmail());
	    	oldUser.getEmployee().setLeaveEntitled(updateUser.getEmployee().getLeaveEntitled());
	    	oldUser.getEmployee().setName(updateUser.getEmployee().getName());
	    	oldUser.getEmployee().setReportsTo(updateUser.getEmployee().getReportsTo());
	    	oldUser.setRole(updateUser.getRole());
	    	credRepo.save(oldUser);
	    }
	    return "redirect:/adminhome";
	}
	
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable(name = "id") long id) {
		credRepo.delete(credRepo.findById(id).orElse(null));
        return "redirect:/adminhome";
    }
	
	@GetMapping("/leavetype")
	public String showLeaveType(Model model) {
		List<LeaveType> lt = leaveTypeRepo.findAll();
		model.addAttribute("leavetypes", lt);		
		return "leavetypes";
	}
	
	@GetMapping("/addleavetype")
	public String addLeaveType(Model model) {
		
		model.addAttribute("form",new LeaveType());
		
		return "addleavetype";
	}
	
	@PostMapping("/addleavetype")
	public String addLeaveTypes(@ModelAttribute("form") LeaveType form, Model model) {
		
		leaveTypeRepo.save(form);
		
		return "redirect:/leavetype";
	}
	
	@GetMapping("/leavetypeedit{id}")
	public String editleavetype(@PathVariable("id") long id, Model model) {
	    LeaveType lt = leaveTypeRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Leave Type Id:" + id));	    
	    model.addAttribute("user", lt);
	    return "editleavetype";
	}
	@PostMapping("/leavetypeedit{id}")
	public String updateUser(@ModelAttribute("updateUser") LeaveType updateLeaveType, @PathVariable("id") long id, @Valid Credentials user, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        user.setUserId(id);
	        return "editleavetype";
	    } else {
	    	LeaveType oldLeaveType = leaveTypeRepo.findById(id)
	    			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    	
	    	oldLeaveType.setName(updateLeaveType.getName());
	    	leaveTypeRepo.save(oldLeaveType);
	    }
	    return "redirect:/leavetype";
	}
	
	@RequestMapping(path = "/leavetypedelete{id}", method = RequestMethod.GET)
    public String deleteleavetype(@PathVariable(name = "id") long id) {
		leaveTypeRepo.delete(leaveTypeRepo.findById(id).orElse(null));
        return "redirect:/leavetype";
    }
	

	@GetMapping("/publicholidays")
	public String showAllPublicHolidays(Model model) {
		List<PublicHoliday> ph = phRepo.findAll();
		model.addAttribute("holidays", ph);		
		return "publicholidays";
	}
	
	@GetMapping("/publicholidaysadd")
	public String addPublicHolidays(Model model) {
		
		model.addAttribute("form",new PublicHoliday());
		
		return "publicholidaysadd";
	}
	
	@PostMapping("/publicholidaysadd")
	public String addPublicHolidayss(@ModelAttribute("form") PublicHoliday form, Model model) {
		
		form.setDate(form.getDate().plusDays(1));
		phRepo.save(form);
		
		return "redirect:/publicholidays";
	}
	
	@GetMapping("/publicholidayedit{id}")
	public String editholiday(@PathVariable("id") long id, Model model) {
	    PublicHoliday ph = phRepo.findById(id);	       
	    model.addAttribute("holiday", ph);
	    return "publicholidayedit";
	}
	@PostMapping("/publicholidayedit{id}")
	public String updateholiday(@ModelAttribute("updateUser") PublicHoliday updateholiday, @PathVariable("id") long id,  
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	       
	        return "publicholidayedit";
	    } else {
	    	PublicHoliday oldpublicholiday = phRepo.findById(id);    	
	    	oldpublicholiday.setDate(updateholiday.getDate());
	    	oldpublicholiday.setDescription(updateholiday.getDescription());
	    	phRepo.save(oldpublicholiday);
	    }
	    return "redirect:/publicholidays";
	}
	
	@RequestMapping(path = "/publicholidaydelete{id}", method = RequestMethod.GET)
    public String deletepublicholiday(@PathVariable(name = "id") long id) {
		phRepo.delete(phRepo.findById(id));
        return "redirect:/publicholidays";
    }
	

	@GetMapping("/denyaccess")
	public String denyAccess() {
		
		return "denyaccess";
	}

	
}
