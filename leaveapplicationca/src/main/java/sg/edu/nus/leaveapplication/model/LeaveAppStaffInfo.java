package sg.edu.nus.leaveapplication.model;

public class LeaveAppStaffInfo {
	private LeaveApplication leaveApplication;
	private String staffName;
	public LeaveApplication getLeaveApplication() {
		return leaveApplication;
	}
	public void setLeaveApplication(LeaveApplication leaveApplication) {
		this.leaveApplication = leaveApplication;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public LeaveAppStaffInfo(LeaveApplication leaveApplication, String staffName) {
		super();
		this.leaveApplication = leaveApplication;
		this.staffName = staffName;
	}
	public LeaveAppStaffInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
