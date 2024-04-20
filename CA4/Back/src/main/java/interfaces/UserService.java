package interfaces;

import models.User;

public interface UserService {
    void addUser(User user) throws Exception;
    boolean login(String username, String password) throws Exception;
}
