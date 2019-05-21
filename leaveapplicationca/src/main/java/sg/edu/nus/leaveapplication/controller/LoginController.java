package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.CA.model.Credentials;
import sg.edu.nus.CA.repos.CredentialsRepository;

@Controller
public class LoginController {
	
	
	private CredentialsRepository userRepos;
	@Autowired
	public void setUserRepos(CredentialsRepository userRepos) {
		this.userRepos = userRepos;
	}

	@GetMapping(path="/login")
	public String login(Model model) {
		model.addAttribute("cred", new Credentials());
		return "login";
	}
	
	@PostMapping(path="/login")
	public String login(@ModelAttribute("user") Credentials user) {
		List<Credentials> u = userRepos.findByUsername(user.getUsername());
		if(u.get(0).getPassword().equals(user.getPassword())) {
			
			return "page";	
		}
		else
			return "login";
	}

}
