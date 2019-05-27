package sg.edu.nus.leaveapplication.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.leaveapplication.model.PublicHoliday;

public interface PHRepository extends JpaRepository<PublicHoliday, Long>{


	ArrayList<PublicHoliday> findAll();
	
	PublicHoliday findById(long id);

}
