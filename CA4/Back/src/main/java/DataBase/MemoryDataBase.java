package DataBase;

import interfaces.DataBase;
import models.*;

import java.util.ArrayList;
import java.util.stream.Stream;

public class MemoryDataBase implements DataBase {
    private static DataBase instance = null;
    private ArrayList<User> users;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Table> tables;
    private ArrayList<TableReservation> reservations;

    private ArrayList<Feedback> feedbacks;
    private int reservationCounter = 0;

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
        users.add(user);
    }
    @Override
    public Stream<Restaurant> getRestaurants() { return restaurants.stream(); }
    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    @Override
    public Stream<Table> getTables() {
        return tables.stream();
    }

    @Override
    public void saveTable(Table table) {
        tables.add(table);
    }

    @Override
    public Stream<TableReservation> getReservations() {
        return reservations.stream();
    }

    @Override
    public void saveReservation(TableReservation reservation) {
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
        feedbacks.add(feedback);
    }
    @Override
    public void deleteFeedback(Feedback feedback) { feedbacks.remove(feedback); }
    @Override
    public void deleteRestaurant(Restaurant restaurant) { restaurants.remove(restaurant); }
}
