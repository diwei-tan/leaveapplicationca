package sg.edu.nus.leaveapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.util.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;
	private LeaveRepository leaveRepo;
	private EmployeeRepository empRepo;
	private CredentialsRepository credRepo;

	@Autowired
	public void setEmployeeRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}

	@Autowired
	public void setCredRepo(CredentialsRepository credRepo) {
		this.credRepo = credRepo;
	}

	@Autowired
	public void setLeaveRepo(LeaveRepository leaveRepo) {
		this.leaveRepo = leaveRepo;
	}

	@GetMapping("/emplist")
	public void exportCsv(HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<Employee> users = empRepo.findAll();
		String fileName = "EmployeeRecord.csv";

		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
		FileCopyUtils.copy(fileService.exportUsersToCsv(users), response.getOutputStream());
	}

	@GetMapping("/leavehistorydl")
	public void exportLeaveHistoryCsv(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Credentials user = credRepo.findByUsername(name);
		List<LeaveApplication> leaveList = leaveRepo.findByUserId(user.getUserId());
		String fileName = "LeaveHistory.csv";

		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
		FileCopyUtils.copy(fileService.exportLeaveHistoryToCsv(leaveList), response.getOutputStream());
	}
	
	@GetMapping("/subleavehistorydl")
	public void exportSubLeaveHistoryCsv(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Credentials user = credRepo.findByUsername(name);
		List<LeaveApplication> subleaveList = leaveRepo.findSubLeaveHistory(user.getUserId());
		String fileName = "TeamLeaveHistory.csv";

		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
		FileCopyUtils.copy(fileService.exportLeaveHistoryToCsv(subleaveList), response.getOutputStream());
	}

}
