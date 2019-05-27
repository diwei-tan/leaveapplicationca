package sg.edu.nus.leaveapplication.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.leaveapplication.LeaveapplicationcaApplication;
import sg.edu.nus.leaveapplication.model.ClaimCompensation;
import sg.edu.nus.leaveapplication.model.ComClaimStaffInfo;
import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveAppStaffInfo;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.model.LeaveType;
import sg.edu.nus.leaveapplication.model.PagerModel;
import sg.edu.nus.leaveapplication.repo.ClaimCompensationRepository;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.util.LeaveFormValidator;
import sg.edu.nus.leaveapplication.repo.LeaveTypeRepository;
import sg.edu.nus.leaveapplication.util.LeaveServices;
import sg.edu.nus.leaveapplication.util.NotificationService;

@Controller
public class LeaveApplicationController {
	Logger LOG = LoggerFactory.getLogger(LeaveapplicationcaApplication.class);
	
	private CredentialsRepository credRepo;
	private EmployeeRepository employeeRepo;
	private LeaveRepository leaveRepo;
	private LeaveTypeRepository leaveTypeRepo;
	private ClaimCompensationRepository claimComRepo;
	
	@Autowired
	LeaveFormValidator leaveValidator;
	
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
	
	@Autowired
	public void setLeaveTypeRepo(LeaveTypeRepository leaveTypeRepo) {
		this.leaveTypeRepo = leaveTypeRepo;
	}
	
	@Autowired
	public void setClaimComRepo(ClaimCompensationRepository claimComRepo) {
		this.claimComRepo = claimComRepo;
	}


	@Autowired
	private NotificationService notificationService;

	
	@GetMapping(path="/home")
	public String leaveMainPage(Model model,Principal principal) {
		//Should anyone be able to login, directed to this page to show the leave information of themselves
		//should show approved list of leave application that are not yet past
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Credentials user = credRepo.findByUsername(name);
		model.addAttribute("user", user);
		List<LeaveApplication>leaveList = leaveRepo.findByUserId(user.getUserId());
		leaveList = leaveList.stream().filter(x->x.getStatus().equals("Applied")||
				x.getStatus().equals("Approved")).collect(Collectors.toList());
		model.addAttribute("leaveList", leaveList);
		return "home";
	}
	//edited with pagination//
	
	//test pagination using /home1//
		private static final int BUTTONS_TO_SHOW = 3;
	    private static final int INITIAL_PAGE = 0;
	    private static final int INITIAL_PAGE_SIZE = 5;
	    private static final int[] PAGE_SIZES = { 5, 10};
	    
	@GetMapping(path="/leavehistory")
    public ModelAndView homepage(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView();
        // Evaluate page size. If requested parameter is null, return initial
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo      
        
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	Credentials user = credRepo.findByUsername(name);
        modelAndView.addObject("user", user);
        Page<LeaveApplication> list1 = leaveRepo.findByUserId(user.getUserId(), new PageRequest(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(list1.getTotalPages(),list1.getNumber(),BUTTONS_TO_SHOW);
        // add clientmodel
        modelAndView.addObject("list",list1);
        // evaluate page size
        modelAndView.addObject("selectedPageSize", evalPageSize);
        // add page sizes
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        // add pager
        modelAndView.addObject("pager", pager);
        return modelAndView;
	}
	
	
	//original code//
	//@GetMapping(path="/leavehistory")
	//public String leaveHistoryPage(Model model,Principal principal) {
		//Should anyone be able to login, directed to this page to show the leave information of themselves
		//should show approved list of leave application that are not yet past
	//	String name = SecurityContextHolder.getContext().getAuthentication().getName();
	//	Credentials user = credRepo.findByUsername(name);
	//	model.addAttribute("user", user);
	//	List<LeaveApplication>leaveList = leaveRepo.findByUserId(user.getUserId());
	//	model.addAttribute("leaveList", leaveList);
	//	return "leavehistory";
	//}
	
	@GetMapping(path="/subleavehistory")
    public ModelAndView subhomepage(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView();
        // Evaluate page size. If requested parameter is null, return initial
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo      
        
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	Credentials user = credRepo.findByUsername(name);
        modelAndView.addObject("user", user);
        Page<LeaveApplication> list1 = leaveRepo.findByManagerId(user.getUserId(), new PageRequest(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(list1.getTotalPages(),list1.getNumber(),BUTTONS_TO_SHOW);
        // add clientmodel
        modelAndView.addObject("list",list1);
        // evaluate page size
        modelAndView.addObject("selectedPageSize", evalPageSize);
        // add page sizes
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        // add pager
        modelAndView.addObject("pager", pager);
        return modelAndView;
	}
	
//	@RequestMapping(path="/subleavehistory",method=RequestMethod.GET)
//	public String subleavehistory(@ModelAttribute("form") LeaveApplication form,Model model) {
//		String name = SecurityContextHolder.getContext().getAuthentication().getName();
//		Credentials user = credRepo.findByUsername(name);
//		model.addAttribute("user", user);
//		List<LeaveApplication>leaveList = leaveRepo.findSubLeaveHistory(user.getUserId());
//		model.addAttribute("leaveList", leaveList);
//		return "subleavehistory";
//	}
	
//	@RequestMapping(path="/home",method=RequestMethod.POST)
//	public String returnMainPage(Model model)
//	{
//		List<LeaveApplication>leaveList = leaveRepo.findAll();
//		model.addAttribute("leaveList", leaveList);
//		return "home";
//	}
	
	@GetMapping(path="/create{id}")
	public String loadMethod(@PathVariable("id") long id, Model model) {

		model.addAttribute("form", new LeaveApplication());

		List<LeaveType> lt = leaveTypeRepo.findAll();
		model.addAttribute("leavetypes", lt);	

		return "leaveform";
		
	}


	@PostMapping(path="/create{id}")
	public String processStupidForm(@PathVariable("id") long id, @ModelAttribute("form") LeaveApplication form, 
			BindingResult bindingresult,Model model) {
		Employee emp = employeeRepo.findById(id).orElse(null);
		form.setEmployee(emp);
		LeaveServices service = new LeaveServices();
		try {
			form.setNumDays(service.calculateNumOfLeaveDays(form.getStartDate(), form.getEndDate()));
		}
		catch(Exception e) {
			LOG.info(e.getMessage());
		}
		//if validation error, return to registration will info and errors
		leaveValidator.validate(form, bindingresult);
		if(bindingresult.hasErrors()) {
			model.addAttribute("form", form);
			List<LeaveType> lt = leaveTypeRepo.findAll();
			model.addAttribute("leavetypes", lt);	
			return "leaveform";
		}
		leaveRepo.save(form);
		//if compensation leave, delete compensation hours from staff
		if(form.getType().equals("Compensationleave")) {
			emp.setCompensationhours((long)(emp.getCompensationhours()-(form.getNumDays()*2*4)));
			employeeRepo.save(emp);
		}
		long managerid = emp.getReportsTo();
		Employee emp1 = employeeRepo.findById(managerid).orElse(null);
		String manageremail = emp1.getEmail();
		notificationService.sendAppliedNotification(manageremail);
		return "redirect:/home";
		
	}
	@GetMapping("/leaveedit{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    LeaveApplication leaveApplication = leaveRepo.findById(id);
	    List<LeaveType> lt = leaveTypeRepo.findAll();
		model.addAttribute("leavetypes", lt);	
		model.addAttribute("leaveApplication", leaveApplication);
	    return "editleave";
	}
	
	@PostMapping("/leaveupdate{id}")
	public String updateUser(@ModelAttribute("leaveApplication") LeaveApplication updateLeave, @PathVariable(name="id") long id, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "/leaveedit"+id;
	    } else {
	    	LeaveServices service = new LeaveServices();
	    	LeaveApplication oldLeave= leaveRepo.findById(id);
	    	oldLeave.setStartDate(updateLeave.getStartDate());
	    	oldLeave.setEndDate(updateLeave.getEndDate());
	    	try {
	    	oldLeave.setNumDays(service.calculateNumOfLeaveDays(updateLeave.getStartDate(), updateLeave.getEndDate()));
	    	} catch(Exception e) {
	    		LOG.info(e.getMessage());
	    	}
	    	oldLeave.setReason(updateLeave.getReason());
	    	oldLeave.setStatus(updateLeave.getStatus());
	    	oldLeave.setType(updateLeave.getType());
	    	leaveRepo.save(oldLeave);
	    }
	    return "redirect:/home";
	}
	
	@RequestMapping(path = "/cancel{id}", method = RequestMethod.GET)
    public String deleteProduct(@ModelAttribute("LeaveApplicaiton") LeaveApplication deleteLeave, @PathVariable(name = "id") long id, Model model) {
		LeaveApplication leave = leaveRepo.findById(id);
		leave.setStatus("Cancelled");
		leaveRepo.save(leave);
		Employee emp = employeeRepo.findById(leave.getEmployee().getId()).orElse(null);
		if(leave.getType().equals("Compensationleave")) {
			emp.setCompensationhours((long)(emp.getCompensationhours()+(leave.getNumDays()*2*4)));
			employeeRepo.save(emp);
		}
        return "redirect:/home";
    }
	
	@RequestMapping(path="/managerhome",method=RequestMethod.GET)
	public String approveleave(@ModelAttribute("form") LeaveApplication form,Model model) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Credentials user = credRepo.findByUsername(name);
		List<Employee> employeeList = employeeRepo.findAll();
		List<LeaveApplication> leaveApp = leaveRepo.findByManagerId(user.getUserId());
		List<LeaveAppStaffInfo> l = new ArrayList<LeaveAppStaffInfo>();
		for(LeaveApplication la : leaveApp) {
			Employee e = employeeList.stream().filter(x->x.getId()==la.getEmployee().getId()).findFirst().orElse(null);
			l.add(new LeaveAppStaffInfo(la, e.getName()));
		}
		model.addAttribute("approve", l);
		List<ComClaimStaffInfo> claims = new ArrayList<>();
		List<ClaimCompensation> cc = claimComRepo.findAll();
		for(ClaimCompensation claim : cc) {
			Employee e = employeeList.stream().filter(x->x.getId()==claim.getEmployeeId()).findFirst().orElse(null);
			claims.add(new ComClaimStaffInfo(claim, e.getName()));
		}
		model.addAttribute("approveclaimcom", claims);
		return"approveform";
	}
	
	@RequestMapping(path="/manager/approve/{id}",method=RequestMethod.GET)
	public String acceptleave(@PathVariable(name = "id") long id,LeaveApplication leaveApplication) {
	 leaveApplication = leaveRepo.findById(id);		
	leaveApplication.setStatus("Approved");
	leaveRepo.save(leaveApplication);
	notificationService.sendNotification(leaveApplication);
		return"redirect:/managerhome";
	}
	
	@RequestMapping(path="/manager/reject/{id}",method=RequestMethod.GET)
	public String rejectleave(@PathVariable(name = "id") long id,LeaveApplication leaveApplication) {
	 leaveApplication = leaveRepo.findById(id);	
	leaveApplication.setStatus("Rejected");
	leaveRepo.save(leaveApplication);
	//refund if compensation leave
	if(leaveApplication.getType().equals("Compensationleave")) {
		Employee emp = employeeRepo.findById(leaveApplication.getEmployee().getId()).orElse(null);
		emp.setCompensationhours((long)(emp.getCompensationhours()+(leaveApplication.getNumDays()*2*4)));
		employeeRepo.save(emp);
	}
	notificationService.sendRejectNotification(leaveApplication);
	return"redirect:/managerhome";
	}
	
	//here onwards is for claim compensation ...
	
	@GetMapping(path="/claimform{id}")
	public String claimCompensation(@PathVariable("id") long id, Model model) {
				
		ClaimCompensation claimcom = new ClaimCompensation();
		claimcom.setEmployeeId(id);
		model.addAttribute("claimcom", claimcom);
		
		return "claimform";
		
	}
	
	@PostMapping(path="/leaveapplication/claim{id}")
	public String submitClaimCom(@PathVariable("id") long id, @ModelAttribute("claimcom") ClaimCompensation claimcom) {
		
		ClaimCompensation claimCom = new ClaimCompensation();
		claimCom.setEmployeeId(id);
		claimCom.setCompensationhours(claimcom.getCompensationhours());
		claimCom.setReplied(false);
		claimComRepo.save(claimCom);
		
		return "redirect:/home";
	}
	
	@RequestMapping(path="/manager/approveclaim/{id}",method=RequestMethod.GET)
	public String acceptleave(@PathVariable(name = "id") long id,ClaimCompensation cc) {
		
		ClaimCompensation ccom = claimComRepo.findById(id).get();
		ccom.setStatus("Approved");
		ccom.setReplied(true);
		claimComRepo.save(ccom);
		
		Employee emp = employeeRepo.findById(ccom.getEmployeeId()).get();
		Long num = emp.getCompensationhours();
		Long num1 = ccom.getCompensationhours();
		Long totalnum = num + num1;
		emp.setCompensationhours(totalnum);
		employeeRepo.save(emp);
		
		return "redirect:/managerhome";		

	}
	
	@RequestMapping(path="/manager/rejectclaim/{id}",method=RequestMethod.GET)
	public String rejectleave(@PathVariable(name = "id") long id) {
	
		ClaimCompensation cCom = claimComRepo.findById(id).get();
		cCom.setStatus("Rejected");
		cCom.setReplied(true);
		claimComRepo.save(cCom);		
	
		return"redirect:/managerhome";
	
	}
	
	//...this is the end of the 4 methods for claim compensation
	
	
}
