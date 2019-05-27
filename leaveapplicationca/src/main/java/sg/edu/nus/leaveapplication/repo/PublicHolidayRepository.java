package sg.edu.nus.leaveapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.leaveapplication.model.PublicHoliday;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long>{

}
