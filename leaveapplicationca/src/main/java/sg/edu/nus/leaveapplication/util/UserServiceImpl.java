package sg.edu.nus.leaveapplication.util;


import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CredentialsRepository credRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Credentials user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        credRepository.save(user);
    }

    @Override
    public Credentials findByUsername(String username) {
        return credRepository.findByUsername(username);
    }
}