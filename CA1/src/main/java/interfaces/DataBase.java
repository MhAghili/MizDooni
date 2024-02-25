package interfaces;

import models.Restaurant;
import models.Table;
import models.TableReservation;
import models.User;

import java.util.stream.Stream;

public interface DataBase {
    Stream<User> getUsers();
    void saveUser(User user);
    Stream<Restaurant> getRestaurants();
    void saveRestaurant(Restaurant restaurant);
    Stream<Table> getTables();
    void saveTable(Table table);
    Stream<TableReservation> getReservations();
    void saveReservation(TableReservation reservation);
    void deleteReservation(String username, int reservationNumber);
}
