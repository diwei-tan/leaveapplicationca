package sg.edu.nus.leaveapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="claimcompensation")
public class ClaimCompensation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long employeeId;
	private long compensationhours;
	private String status;
	private boolean replied = false;
	
	public ClaimCompensation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClaimCompensation(long employeeId, long compensationhours, String status, boolean replied) {
		super();
		this.employeeId = employeeId;
		this.compensationhours = compensationhours;
		this.status = status;
		this.replied = replied;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public long getCompensationhours() {
		return compensationhours;
	}

	public void setCompensationhours(long compensationhours) {
		this.compensationhours = compensationhours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isReplied() {
		return replied;
	}

	public void setReplied(boolean replied) {
		this.replied = replied;
	}

	@Override
	public String toString() {
		return "ClaimCompensation [id=" + id + ", employeeId=" + employeeId + ", compensationhours=" + compensationhours
				+ ", status=" + status + ", replied=" + replied + "]";
	}
	

}
