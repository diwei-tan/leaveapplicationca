package sg.edu.nus.leaveapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.leaveapplication.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials,Long>{

	Credentials findByUsername(String username);
	
}
