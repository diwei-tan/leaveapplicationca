package sg.edu.nus.leaveapplication.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.leaveapplication.model.PublicHoliday;

public interface PHRepository extends JpaRepository<PublicHoliday, Long>{	
	PublicHoliday findById(long id);
	
	@Query(value="select ph from PublicHoliday ph")
	List<PublicHoliday> findAllHolidays();

}
