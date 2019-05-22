package sg.edu.nus.leaveapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.leaveapplication.model.LeaveApplication;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Integer>{
	List<LeaveApplication> findByUserId(long id);
}
