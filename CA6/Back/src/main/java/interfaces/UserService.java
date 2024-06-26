package interfaces;

import models.User;

import java.util.List;

public interface UserService {
    void addUser(User user) throws Exception;
    void save(List<User> users) throws Exception;
    void delete(String username) throws Exception;
    boolean login(String username, String password) throws Exception;
    List<User> getAllUsers();
    User getUserByName(String name) throws Exception;


}
