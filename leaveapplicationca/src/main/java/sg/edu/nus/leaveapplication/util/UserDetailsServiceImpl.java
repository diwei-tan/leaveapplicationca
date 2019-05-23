package sg.edu.nus.leaveapplication.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Role;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.RoleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private CredentialsRepository credRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Credentials user = credRepo.findByUsername(username);
        if (user == null) 
        	throw new UsernameNotFoundException(username);
        System.out.println("Found User: " + user.getUsername() + ", " + user.getPassword());
        
        //grant authority accordingly to user roles. if null, then user has no roles
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        if(!user.getRoles().isEmpty()) {
        	for (Role role : user.getRoles()){
        		grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        	}
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}