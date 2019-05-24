package sg.edu.nus.leaveapplication.util;

import org.springframework.stereotype.Service;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.util.CsvUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


	@Service
	public class FileService {


	    public byte[] exportUsersToCsv(List<Credentials> cre) {
	        // 为了方便，也不写dao层
	        List<LinkedHashMap<String, Object>> exportData = new ArrayList<>(cre.size());
	        // 行数据
	        for (Credentials user : cre) {
	            LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();
	            rowData.put("1", user.getUserId());
	            rowData.put("2", user.getUsername());
	            rowData.put("3", user.getPassword());
	            exportData.add(rowData);
	        }
	        LinkedHashMap<String, String> header = new LinkedHashMap<>();
	        header.put("1", "用户账号");
	        header.put("2", "用户昵称");
	        header.put("3", "用户密码");
	        return CsvUtils.exportCSV(header, exportData);
	    }

	}

