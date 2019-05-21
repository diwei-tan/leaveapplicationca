package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.leaveapplication.model.User;
import sg.edu.nus.leaveapplication.repo.userRepository;
@Controller

public class userController {
	
	private  userRepository userRepo;

	@Autowired
	public void setUserRepo(userRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@RequestMapping("/adminhome")
	public void homepage() {
		
	}
	
	@GetMapping("/https://github.com/tanddoubleu/leaveapplicationca.git")	
	public String showadduserform(User user,Model model) {
		List<User> u = userRepo.findByRole(); 
		model.addAttribute("roles", u);
		return "adduser";
	}
	
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "adduser";
        }    
            
        model.addAttribute("users", userRepo.findAll());
        userRepo.save(user);       
        return "adminhome";
    }
	
	@RequestMapping(path="/viewusers" ,method = RequestMethod.GET)
	public String listmethod(Model model) {		
		model.addAttribute("users", userRepo.findAll());
		return "viewusers";
	
	}
	
	@GetMapping("/users/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    User user = userRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    List<User> u = userRepo.findByRole(); 
		model.addAttribute("roles", u);
	    model.addAttribute("user", user);
	    return "edituser";
	}
	
	@PostMapping("/users/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        user.setId(id);
	        return "edituser";
	    }
	         
	    
	    List<User> u = userRepo.findByRole(); 
		model.addAttribute("roles", u);
	    model.addAttribute("users", userRepo.findAll());
	    userRepo.save(user);
	    return "adminhome";
	}
	
	@RequestMapping(path = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable(name = "id") long id) {
        userRepo.delete(userRepo.findById(id).orElse(null));
        return "adminhome";
    }
	
	
	
}
