package DataBase;

import interfaces.DataBase;
import models.Restaurant;
import models.Table;
import models.TableReservation;
import models.User;

import java.util.ArrayList;
import java.util.stream.Stream;

public class MemoryDataBase implements DataBase {
    private ArrayList<User> users;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Table> tables;
    private ArrayList<TableReservation> reservations;
    private int reservationCounter = 0;

    public MemoryDataBase() {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
        tables = new ArrayList<>();
        reservations = new ArrayList<>();
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
    public int getReservationCounter() {
        return ++reservationCounter;
    }
}
