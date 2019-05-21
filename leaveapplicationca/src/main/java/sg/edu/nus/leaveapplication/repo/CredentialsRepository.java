package sg.edu.nus.leaveapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.CA.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials,String>{

	List<Credentials> findByUsername(String username);
	
}
