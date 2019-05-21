package sg.edu.nus.leaveapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.leaveapplication.model.User;

public interface userRepository extends JpaRepository<User, Long> {

	@Query(value = "Select u from User u where u.role = 'Manager'")
	List<User> findByRole();
}
