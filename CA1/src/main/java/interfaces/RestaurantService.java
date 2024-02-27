package interfaces;

import exceptions.InvalidRestaurantName;
import models.Restaurant;
import models.Table;
import models.TableReservation;
import models.ReservationCancellationRequest;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(Restaurant restaurant) throws Exception;
    void addTable(Table table) throws Exception;
    int reserveTable(TableReservation reservation) throws Exception;
    void cancelReservation(ReservationCancellationRequest request) throws Exception;
    List<Restaurant> getRestaurantByType(String type);
    Restaurant getRestaurantByName(String name) throws Exception;
    List<TableReservation> getReservationByUsername(String username);
}
