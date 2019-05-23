package sg.edu.nus.leaveapplication.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.leaveapplication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
