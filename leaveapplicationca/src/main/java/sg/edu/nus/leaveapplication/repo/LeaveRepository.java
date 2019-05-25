package sg.edu.nus.leaveapplication.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.model.LeaveApplication;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Integer>{
	List<LeaveApplication> findByEmployee(Employee e);
	
	@Query(value="select  lt from LeaveApplication lt,Employee e where lt.employee.reportsTo = ?1 and lt.status='applied'")
	List<LeaveApplication> findByManagerId(long id);


	Optional<LeaveApplication> findById(long id);
	
	@Query(value="select  lt from LeaveApplication lt where lt.employee.id = ?1")
	List<LeaveApplication> findByUserId(long id);
}
