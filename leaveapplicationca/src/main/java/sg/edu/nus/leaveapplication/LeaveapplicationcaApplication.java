package sg.edu.nus.leaveapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LeaveapplicationcaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LeaveapplicationcaApplication.class, args);
	}
}
