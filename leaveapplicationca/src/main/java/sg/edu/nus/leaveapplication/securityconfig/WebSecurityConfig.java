package sg.edu.nus.leaveapplication.securityconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import sg.edu.nus.leaveapplication.util.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = UserDetailsServiceImpl.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }
    
//    @Value("${spring.queries.users-query}")
//    private String usersQuery;
//
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("STAFF")
          .and()
          .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("STAFF")
          .and()
          .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");

        auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username,password,1 from credentials where username = ? ")
		.authoritiesByUsernameQuery("SELECT username, role from credentials where username = ? ")
		.passwordEncoder(passwordEncoder());
        
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
        http
        	.authorizeRequests()
        	.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
        	.and()
        	.authorizeRequests().antMatchers("/home","/create**","/leaveedit**", "/leavehistory**"
        			, "/leaveupdate**","/cancel**", "/claim**", "/leaveapplicaiton**").hasAnyRole("STAFF,MANAGER")
        	.and()
        	.authorizeRequests().antMatchers("/manager**", "/subleavehistory**").hasRole("MANAGER")
        	.and()
        	.authorizeRequests().antMatchers("/adminhome", "/edit**", "/delete**", "/update**","/leavetype**", "/adduser"
        			,"/addleavetype**","/leavetype**","/publicholiday**","/emplist").hasRole("ADMIN")
        	.and()
        	.formLogin()
        	.loginProcessingUrl("/j_spring_security_check")
        	.loginPage("/login")
        	.permitAll()
        	.defaultSuccessUrl("/home")
        	.failureUrl("/login?error=true")
        	.successHandler(myAuthenticationSuccessHandler())
        	.and()
        	.logout().invalidateHttpSession(true).clearAuthentication(true)
        	.logoutSuccessUrl("/login?logout=true")
        	.permitAll()
        	.and().exceptionHandling().accessDeniedPage("/denyaccess");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
}