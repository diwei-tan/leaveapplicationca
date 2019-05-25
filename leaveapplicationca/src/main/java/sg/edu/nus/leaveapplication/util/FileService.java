package sg.edu.nus.leaveapplication.util;

import org.springframework.stereotype.Service;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
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

	}

