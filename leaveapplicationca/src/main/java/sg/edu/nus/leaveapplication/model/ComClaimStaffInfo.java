package sg.edu.nus.leaveapplication.model;


public class ComClaimStaffInfo {
	ClaimCompensation claim;
	String employeeName;
	public ClaimCompensation getClaim() {
		return claim;
	}
	public void setClaim(ClaimCompensation claim) {
		this.claim = claim;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public ComClaimStaffInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComClaimStaffInfo(ClaimCompensation claim, String employeeName) {
		super();
		this.claim = claim;
		this.employeeName = employeeName;
	}
	
	
}
