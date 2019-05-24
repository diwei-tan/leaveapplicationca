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

import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;
@Controller

public class AdminController {
	
	private  EmployeeRepository employeeRepo;

	@Autowired
	public void setEmployeeRepo(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}
	
//	@RequestMapping("/adminhome")
//	public void homepage() {
//		
//	}
	
	@GetMapping("/adduser")	
	public String showadduserform(Model model) {
		List<Employee> u = employeeRepo.findByRole(); 
		model.addAttribute("roles", u);
		model.addAttribute("form", new Employee());
		return "adduser";
	}
	
	@PostMapping("/adduser")
	public String addUser(@Valid Employee user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "adduser";
        }    
        employeeRepo.save(user);   
        model.addAttribute("users", employeeRepo.findAll());
        
        return "adminhome";
    }
	
	@RequestMapping(path="/adminhome" ,method = RequestMethod.GET)
	public String listmethod(Model model) {		
		model.addAttribute("users", employeeRepo.findAll());
		return "adminhome";
	
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Employee user = employeeRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    List<Employee> u = employeeRepo.findByRole(); 
		model.addAttribute("roles", u);
	    model.addAttribute("user", user);
	    return "edituser";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid Employee user, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        user.setId(id);
	        return "edituser";
	    }
	         
	    
	    List<Employee> u = employeeRepo.findByRole(); 
		model.addAttribute("roles", u);		
	    model.addAttribute("users", employeeRepo.findAll());
	    employeeRepo.save(user);
	    return "adminhome";
	}
	
	@RequestMapping(path = "/delete{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable(name = "id") long id) {
        employeeRepo.delete(employeeRepo.findById(id).orElse(null));
        return "adminhome";
    }
	
	
	
}
