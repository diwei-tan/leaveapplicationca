package sg.edu.nus.leaveapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;	
	@NotNull
	@Size(min=2,max=100)
	private String name;	
	private String role;	
	private Integer leaveEntitled;	
	private long contact;	
	private String email;
	private double compensationhours;	
	private String reportsTo;
	
	
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public User(String name, String role, Integer leaveEntitled, String email, String reportsTo) {
		super();
		this.name = name;
		this.role = role;
		this.leaveEntitled = leaveEntitled;
		this.email = email;
		this.reportsTo = reportsTo;
	}

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}	

	public Integer getLeaveEntitled() {
		return leaveEntitled;
	}

	public void setLeaveEntitled(Integer leaveEntitled) {
		this.leaveEntitled = leaveEntitled;
	}

	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getCompensationhours() {
		return compensationhours;
	}
	public void setCompensationhours(double compensationhours) {
		this.compensationhours = compensationhours;
	}
	
	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}

	
	
}
