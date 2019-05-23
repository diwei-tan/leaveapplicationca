package sg.edu.nus.leaveapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class LeaveapplicationcaApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LeaveapplicationcaApplication.class, args);
    }
    
}
