package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.util.SecurityService;
import sg.edu.nus.leaveapplication.util.UserService;
import sg.edu.nus.leaveapplication.util.UserValidator;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;


@Controller
public class LoginController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
	
	private CredentialsRepository credRepos;
	@Autowired
	public void setCredRepos(CredentialsRepository credRepos) {
		this.credRepos = credRepos;
	}
	private EmployeeRepository employeeRepos;
	@Autowired
	public void setUserRepos(EmployeeRepository employeeRepos) {
		this.employeeRepos = employeeRepos;
	}
	private LeaveRepository leaveRepos;
	@Autowired
	public void setLeaveRepos(LeaveRepository leaveRepos) {
		this.leaveRepos = leaveRepos;
	}

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new Credentials());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") Credentials userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/login";
    }
	
	
	@GetMapping({"/", "/login"})
	public String login(Model model, String error, String logout) {
		if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
	}
	
	
	@PostMapping("/login") 
	public String login(@ModelAttribute("cred") Credentials user, Model model) { 
		Credentials u =credRepos.findByUsername(user.getUsername()); 
		if(u == null) 
			return "login";
		else if(u.getPassword().equals(user.getPassword())) { 
			//Pass User and Leave by user to view //definitely one UserId(one to one between credentials and user) 
			Employee loginUser = employeeRepos.findById(u.getUserId()).get();
			List<LeaveApplication> leaveList = leaveRepos.findByUserId(loginUser.getId()); 
			model.addAttribute("loginUser", loginUser); 
			model.addAttribute("leaveList", leaveList); return "home"; 
		} 
		else 
			return "login";
	}
	
}
