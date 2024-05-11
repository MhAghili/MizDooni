package DataBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import interfaces.DataBase;
import models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Stream;
import jakarta.persistence.*;

@Service
public class MemoryDataBase implements DataBase {
    private static DataBase instance = null;
    private ArrayList<User> users;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<RestaurantTable> tables;
    private ArrayList<TableReservation> reservations;

    private ArrayList<Feedback> feedbacks;
    private int reservationCounter = 0;

    Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    private MemoryDataBase() {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
        tables = new ArrayList<>();
        reservations = new ArrayList<>();
        feedbacks = new ArrayList<>();
    }

    public static DataBase getInstance() {
        if (instance == null)
            instance = new MemoryDataBase();
        return instance;
    }

    @Override
    public Stream<User> getUsers() {

        return users.stream();
    }

    @Override
    public void saveUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User existingUser = session.get(User.class, user.getUsername());
            if (existingUser != null) {
                session.merge(user);
            } else {
                session.save(user);
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        users.add(user);
    }
    @Override
    public Stream<Restaurant> getRestaurants() { return restaurants.stream(); }
    @Override
    public void saveRestaurant(Restaurant restaurant) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Restaurant existingRestaurant = session.get(Restaurant.class, restaurant.getName());
            if (existingRestaurant != null) {
                session.merge(restaurant);
            } else {
                session.save(restaurant);
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        restaurants.add(restaurant);
    }

    @Override
    public Stream<RestaurantTable> getTables() {
        return tables.stream();
    }

    @Override
    public void saveTable(RestaurantTable table) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (table.getId() != null) {
                RestaurantTable existingRestaurantTable = session.get(RestaurantTable.class, table.getId());
                if (existingRestaurantTable != null) {
                    session.merge(table);
                } else {
                    session.save(table);
                }

            }
            else {
                session.save(table);
            }
            transaction.commit();
            tables.add(table);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public Stream<TableReservation> getReservations() {
        return reservations.stream();
    }

    @Override
    public void saveReservation(TableReservation reservation) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            TableReservation existingTableReservation = session.get(TableReservation.class, reservation.getNumber());
            if (existingTableReservation != null) {
                session.merge(reservation);
            } else {
                session.save(reservation);
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        reservations.add(reservation);
    }

    @Override
    public void deleteReservation(String username, int reservationNumber) {
        reservations.removeIf(i -> i.getUsername().equals(username) && i.getNumber() == reservationNumber);
    }
    @Override
    public void deleteUser(User user) { users.remove(user); }
    @Override
    public int getReservationCounter() {
        return ++reservationCounter;
    }

    @Override
    public Stream<Feedback> getFeedbacks() {
        return feedbacks.stream();
    }

    @Override
    public void saveFeedback(Feedback feedback){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (feedback.getId() != null) {
                Feedback existingFeedback = session.get(Feedback.class, feedback.getId());
                if (existingFeedback != null) {
                    session.merge(feedback);
                } else {
                    session.save(feedback);
                }
            }
            else {
                session.save(feedback);
            }
            transaction.commit();
            feedbacks.add(feedback);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }


    }
    @Override
    public void deleteFeedback(Feedback feedback) { feedbacks.remove(feedback); }
    @Override
    public void deleteRestaurant(Restaurant restaurant) { restaurants.remove(restaurant); }
}

