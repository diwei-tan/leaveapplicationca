package sg.edu.nus.leaveapplication.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.LeaveApplication;



@Service
public class NotificationService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(LeaveApplication leaveApplication){
		//send email
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(leaveApplication.getEmployee().getEmail());
		mail.setFrom("leaveapplicationtest1@gmail.com");
		mail.setSubject("Leave Approval");
		mail.setText("Your Leave is Approved ");
		 
		javaMailSender.send(mail);
		
	}
	
	public void sendRejectNotification(LeaveApplication leaveApplication){
		//send email
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(leaveApplication.getEmployee().getEmail());
		mail.setFrom("leaveapplicationtest1@gmail.com");
		mail.setSubject("Leave Approval");
		mail.setText("Your Leave is Rejected ");
		 
		javaMailSender.send(mail);
		
	}
	
	public void sendAppliedNotification(String leaveApplication){
		//send email
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(leaveApplication);
		mail.setFrom("leaveapplicationtest1@gmail.com");
		mail.setSubject("Leave Applied");
		mail.setText("You have pending leave requests to be Approved ");
		 
		javaMailSender.send(mail);
		
	}

}