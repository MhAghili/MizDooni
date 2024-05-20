package services;

import enums.UserType;
import exceptions.*;
import interfaces.UserService;
import models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import utils.Utils;
import models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImplementation implements UserService {
    private static UserService instance;

    private final SessionFactory sessionFactory;
    private UserServiceImplementation() {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserServiceImplementation();
        return instance;
    }
    @Override
    public void addUser(User user) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User existingUser = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", user.getUsername())
                    .uniqueResultOptional()
                    .orElse(null);

            if (existingUser != null) {
                throw new UsernameAlreadyTaken();
            }

            existingUser = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", user.getEmail())
                    .uniqueResultOptional()
                    .orElse(null);

            if (existingUser != null) {
                throw new EmailAlreadyTaken();
            }

            if (user.getRole() == UserType.other) {
                throw new InvalidUserType();
            }

            if (!isValidEmail(user.getEmail())) {
                throw new InvalidEmailFormat();
            }

            if (!isValidUsername(user.getUsername())) {
                throw new InvalidUsernameFormat();
            }

            if (Utils.isNullOrEmptyString(user.getAddress().getCity()) || Utils.isNullOrEmptyString(user.getAddress().getCountry())) {
                throw new AddressShouldContainsCityAndCountry();
            }

            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void save(List<User> users) throws Exception {
        for (var user : users) {
            addUser(user);
        }
    }


    @Override
    public void delete(String username) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional()
                    .orElse(null);

            if (user == null) {
                throw new InvalidUsernameFormat();
            }

            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class)
                    .list();
        }
    }


    @Override
    public User getUserByNameAndPassword(String name, String password) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User WHERE username = :name AND password = :password", User.class)
                    .setParameter("name", name)
                    .setParameter("password", password)
                    .uniqueResultOptional()
                    .orElseThrow(UserNotFound::new);
            return user;
        }
    }

    @Override
    public boolean login(String username, String password) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional()
                    .orElseThrow(UserNotFound::new);

            if (!user.getPassword().equals(password)) {
                throw new InvalidPassword();
            }

            return true;
        }
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
