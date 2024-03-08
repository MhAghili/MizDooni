package interfaces;

import models.*;
import utils.ReservationCancellationRequest;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(Restaurant restaurant) throws Exception;
    void addTable(Table table) throws Exception;
    int reserveTable(TableReservation reservation) throws Exception;
    void cancelReservation(ReservationCancellationRequest request) throws Exception;
    List<Restaurant> getRestaurantByType(String type);
    Restaurant getRestaurantByName(String name) throws Exception;
    List<TableReservation> getReservationByUsername(String username);
    List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName) throws Exception;
}
