package sg.edu.nus.leaveapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.leaveapplication.model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long> {

}
