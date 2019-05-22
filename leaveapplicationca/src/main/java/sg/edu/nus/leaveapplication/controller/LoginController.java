package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.model.User;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.repo.UserRepository;


@Controller
public class LoginController {
	
	
	private CredentialsRepository credRepos;
	@Autowired
	public void setCredRepos(CredentialsRepository credRepos) {
		this.credRepos = credRepos;
	}
	private UserRepository userRepos;
	@Autowired
	public void setUserRepos(UserRepository userRepos) {
		this.userRepos = userRepos;
	}
	private LeaveRepository leaveRepos;
	@Autowired
	public void setLeaveRepos(LeaveRepository leaveRepos) {
		this.leaveRepos = leaveRepos;
	}

	@GetMapping(path="/")
	public String login(Model model) {
		model.addAttribute("cred", new Credentials());
		return "login";
	}
	
	@PostMapping(path="/login")
	public String login(@ModelAttribute("cred") Credentials user, Model model) {
		List<Credentials> u = credRepos.findByUsername(user.getUsername());
		if(u.isEmpty())
			return "login";
		else if(u.get(0).getPassword().equals(user.getPassword())) {
			//Pass User and Leave by user to view
			//definitely one UserId(one to one between credentials and user)
			User loginUser = userRepos.findById(u.get(0).getUserId()).get();
			List<LeaveApplication> leaveList = leaveRepos.findByUserId(loginUser.getId());
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("leaveList", leaveList);
			return "home";	
		}
		else
			return "login";
	}

}
