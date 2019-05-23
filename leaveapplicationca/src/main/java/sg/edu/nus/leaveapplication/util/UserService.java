package sg.edu.nus.leaveapplication.util;

import sg.edu.nus.leaveapplication.model.Credentials;

public interface UserService {
    void save(Credentials user);

    Credentials findByUsername(String username);
}