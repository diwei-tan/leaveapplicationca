package sg.edu.nus.leaveapplication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.leaveapplication.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials,Long>{

	
	Credentials findByUsername(String username);
	Credentials findByUserId(Long id);
}
