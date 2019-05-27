package sg.edu.nus.leaveapplication.util;

import org.springframework.stereotype.Service;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.util.CsvUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


	@Service
	public class FileService {


	    public byte[] exportUsersToCsv(List<Employee> users) {
	        // 为了方便，也不写dao层
	        List<LinkedHashMap<String, Object>> exportData = new ArrayList<>(users.size());
	        // 行数据
	        for (Employee user : users) {
	            LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();
	            rowData.put("1", user.getName());
	            rowData.put("2", user.getDesignation());
	            rowData.put("3", user.getLeaveEntitled());
	            rowData.put("4", user.getContact());
	            rowData.put("5", user.getEmail());
	            rowData.put("6", user.getCompensationhours());
	            rowData.put("7", user.getReportsTo());
	            exportData.add(rowData);
	        }
	        LinkedHashMap<String, String> header = new LinkedHashMap<>();
	
	        header.put("1", "Name");
	        header.put("2", "Designation");
	        header.put("3", "LeaveEntitled");
	        header.put("4", "Contact");
	        header.put("5", "Email");
	        header.put("6", "Compensationhours");
	        header.put("7", "getReportsTo");
	        return CsvUtils.exportCSV(header, exportData);
	    }
	    
	    public byte[] exportLeaveHistoryToCsv(List<LeaveApplication> leaveList) {
	        // 为了方便，也不写dao层
	        List<LinkedHashMap<String, Object>> exportData = new ArrayList<>(leaveList.size());
	        // 行数据
	        for (LeaveApplication leave : leaveList) {
	            LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();
	            rowData.put("1", leave.getEmployee().getName());
	            rowData.put("2", leave.getStartDate());
	            rowData.put("3", leave.getEndDate());
	            rowData.put("4", leave.getType());
	            rowData.put("5", leave.getStatus());
	            rowData.put("6", leave.getNumDays());
	            rowData.put("7", leave.getReason());
	            exportData.add(rowData);
	        }
	        LinkedHashMap<String, String> header = new LinkedHashMap<>();
	        header.put("1", "Name");
	        header.put("2", "Start Date");
	        header.put("3", "End Date");
	        header.put("4", "Leave Type");
	        header.put("5", "Status");
	        header.put("6", "Days");
	        header.put("7", "Reason");
	        return CsvUtils.exportCSV(header, exportData);
	    }
	    
	    public byte[] exportSubLeaveHistoryToCsv(List<LeaveApplication> leaveList) {
	        // 为了方便，也不写dao层
	        List<LinkedHashMap<String, Object>> exportData = new ArrayList<>(leaveList.size());
	        // 行数据
	        for (LeaveApplication subleave : leaveList) {
	            LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();
	            rowData.put("1", subleave.getEmployee().getName());
	            rowData.put("2", subleave.getStartDate());
	            rowData.put("3", subleave.getEndDate());
	            rowData.put("4", subleave.getType());
	            rowData.put("5", subleave.getStatus());
	            rowData.put("6", subleave.getNumDays());
	            rowData.put("7", subleave.getReason());
	            exportData.add(rowData);
	        }
	        LinkedHashMap<String, String> header = new LinkedHashMap<>();
	        header.put("1", "Name");
	        header.put("2", "Start Date");
	        header.put("3", "End Date");
	        header.put("4", "Leave Type");
	        header.put("5", "Status");
	        header.put("6", "Days");
	        header.put("7", "Reason");
	        return CsvUtils.exportCSV(header, exportData);
	    }

	}

