package sg.edu.nus.leaveapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = "Select e from Employee e where e.designation = 'Manager'")
	List<Employee> findManagers();
	List<Employee> findByName(String name);
}
