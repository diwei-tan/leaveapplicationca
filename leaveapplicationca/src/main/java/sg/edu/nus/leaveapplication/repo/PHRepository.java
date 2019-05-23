package sg.edu.nus.leaveapplication.repo;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.leaveapplication.model.PublicHoliday;


@Repository
public interface PHRepository extends JpaRepository<PublicHoliday, LocalDate>{
	
	
	ArrayList<PublicHoliday> findAll();
}
