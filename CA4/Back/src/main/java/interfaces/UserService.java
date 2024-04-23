package interfaces;

import models.User;

import java.util.List;

public interface UserService {
    void addUser(User user) throws Exception;
    void save(List<User> users) throws Exception;
    boolean login(String username, String password) throws Exception;
}
