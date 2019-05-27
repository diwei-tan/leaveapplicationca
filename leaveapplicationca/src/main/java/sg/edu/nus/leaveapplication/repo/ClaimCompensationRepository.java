package sg.edu.nus.leaveapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.leaveapplication.model.ClaimCompensation;

@Repository
public interface ClaimCompensationRepository extends JpaRepository<ClaimCompensation,Long> {

}
