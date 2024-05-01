package interfaces;

import models.*;
import utils.ReservationCancellationRequest;

import java.util.List;
import java.util.function.Predicate;

public interface RestaurantService {
    void addRestaurant(Restaurant restaurant) throws Exception;
    void addTable(Table table) throws Exception;
    void save(List<Restaurant> restaurants) throws Exception;
    void updateRestaurant(Restaurant restaurant);
    void saveTables(List<Table> tables) throws Exception;
    int reserveTable(TableReservation reservation) throws Exception;
    void cancelReservation(TableReservation reservation) throws Exception;
    List<Restaurant> getRestaurantByType(String type);
    Restaurant getRestaurantByName(String name) throws Exception;
    List<TableReservation> getReservationByUsername(String username);
    List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName) throws Exception;

    Restaurant getCurrentRes(String restaurantName) throws Exception;

    List<Table> getTablesByRestaurant(String restaurantName) throws Exception;

    List<Restaurant> getRestaurantsByManager(String managerUsername) throws Exception;
    Feedback getAverageFeedbackOfRestaurant(String restaurnatName);

    List<TableReservation> getReservationsByUserName(String userName ) throws Exception;

    List<Table> getTablesByRestaurantName(String restaurantName) throws Exception;
    List<Restaurant> getRestaurantsByCity(String cityName);
    List<Restaurant> fetchAll();
    void delete(String restaurantName) throws Exception;

}
