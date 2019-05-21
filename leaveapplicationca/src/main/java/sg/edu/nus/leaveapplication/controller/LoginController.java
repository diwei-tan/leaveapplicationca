package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.CA.model.User;
import sg.edu.nus.CA.repos.UserRepository;

@Controller
public class LoginController {
	
	
	private UserRepository userRepos;
	@Autowired
	public void setUserRepos(UserRepository userRepos) {
		this.userRepos = userRepos;
	}

	@GetMapping(path="/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping(path="/login")
	public String login(@ModelAttribute("user") User user) {
		List<User> u = userRepos.findByUsername(user.getUsername());
		if(u.get(0).getPassword().equals(user.getPassword())) {
			
			return "page";	
		}
		else
			return "login";
	}

}
