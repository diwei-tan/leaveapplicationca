package sg.edu.nus.leaveapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.leaveapplication.model.ClaimCompensation;
import sg.edu.nus.leaveapplication.model.ComClaimStaffInfo;

@Repository
public interface ClaimCompensationRepository extends JpaRepository<ClaimCompensation, Long>{

}
