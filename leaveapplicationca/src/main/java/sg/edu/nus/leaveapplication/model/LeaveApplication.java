package sg.edu.nus.leaveapplication.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="LEAVETABLE")
public class LeaveApplication {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	@ManyToOne
	@JoinColumn
	Employee employee;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="START_DATE")
	private LocalDateTime startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="END_DATE")
	private LocalDateTime endDate;
	@Column(name="TYPE")
	private String type;
	@Column(name="STATUS")
	private String status;
	@Column(name="NUMDAYS")
	private Double numDays;
	@Column(name="REASON")
	private String reason;
	

	//constructors
	public LeaveApplication(Employee employee, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public LeaveApplication() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//tostring
	@Override
	public String toString() {
		return "LeaveApplication [employee=" + employee.getName() + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	//getters and setters
	public double getNumDays() {
		return numDays;
	}
	public void setNumDays(Double numDays) {
		this.numDays = numDays;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public long getId() {
		return id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
