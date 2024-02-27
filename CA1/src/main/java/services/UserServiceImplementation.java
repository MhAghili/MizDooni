package services;

import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.UserService;
import models.User;
import utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImplementation implements UserService {
    private DataBase dataBase;
    public UserServiceImplementation(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void addUser(User user) throws Exception {
        if (dataBase.getUsers().anyMatch(user1 -> user1.getUsername() == user.getUsername()))
            throw new UsernameAlreadyTaken();

        if (dataBase.getUsers().anyMatch(user1 -> user1.getEmail() == user.getEmail()))
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
