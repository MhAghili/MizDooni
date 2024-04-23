package services;

import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.UserService;
import models.User;
import org.springframework.stereotype.Service;
import utils.Utils;
import DataBase.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImplementation implements UserService {
    private static UserService instance;
    private DataBase dataBase;
    private UserServiceImplementation() {
        this.dataBase = MemoryDataBase.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserServiceImplementation();
        return instance;
    }
    @Override
    public void addUser(User user) throws Exception {
        if (dataBase.getUsers().anyMatch(user1 -> user1.getUsername().equals(user.getUsername())))
            throw new UsernameAlreadyTaken();

        if (dataBase.getUsers().anyMatch(user1 -> user1.getEmail().equals(user.getEmail())))
            throw new EmailAlreadyTaken();

        if (user.getRole() == UserType.other)
            throw new InvalidUserType();

        if (!isValidEmail(user.getEmail()))
            throw new InvalidEmailFormat();

        if (!isValidUsername(user.getUsername()))
            throw new InvalidUsernameFormat();

        if (Utils.isNullOrEmptyString(user.getAddress().getCity()) || Utils.isNullOrEmptyString(user.getAddress().getCountry()))
            throw new AddressShouldContainsCityAndCountry();

        dataBase.saveUser(user);

    }

    @Override
    public void save(List<User> users) throws Exception {
        for (var user : users) {
            dataBase.saveUser(user);
        }
    }

    @Override
    public void save(User user) throws Exception {
        var existingUser = dataBase.getUsers().filter(i -> i.getUsername().equals(user.getUsername())).findFirst();
        if(existingUser.isEmpty())
            dataBase.saveUser(user);
        else {
            dataBase.deleteUser(existingUser.get());
            dataBase.saveUser(user);
        }
    }

    @Override
    public void delete(String username) throws Exception {
        var user = dataBase.getUsers().filter(i -> i.getUsername().equals(username)).findFirst();

        if(user.isEmpty())
            throw new InvalidUsernameFormat();

        dataBase.deleteUser(user.get());
    }

    @Override
    public List<User> getAllUsers() {
        return dataBase.getUsers().toList();
    }

    public boolean login(String username, String password) throws Exception {
        if (dataBase.getUsers().noneMatch(user -> user.getUsername().equals(username)))
            throw new UserNotFound();

        if (dataBase.getUsers().noneMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password)))
            throw new InvalidPassword();

        return true;

    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
