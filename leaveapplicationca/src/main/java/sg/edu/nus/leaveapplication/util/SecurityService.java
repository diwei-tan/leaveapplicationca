package sg.edu.nus.leaveapplication.util;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}