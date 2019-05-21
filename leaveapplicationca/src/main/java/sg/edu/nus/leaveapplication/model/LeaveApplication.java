package sg.edu.nus.leaveapplication.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="LEAVETABLE")
public class LeaveApplication {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="USERID")
	private int userId; // employee that took leave. foreign key to employee
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name="START_DATE")
	private LocalDateTime startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
	public LeaveApplication(int userId, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.userId = userId;
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
		return "LeaveApplication [userId=" + userId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public int getId() {
		return id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	//extra methods overlap to check if dates overlap
	public boolean overlaps(LeaveApplication leaveApplication) {
		//Checks if two leave application coincide
		return(endDate.isAfter(leaveApplication.getStartDate()) && startDate.isBefore(leaveApplication.getEndDate()));
	}
}
