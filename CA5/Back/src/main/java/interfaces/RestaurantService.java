package interfaces;

import models.*;
import utils.ReservationCancellationRequest;
import utils.DTO.RestaurantDTO;

import java.util.Date;
import java.util.List;

public interface RestaurantService {
    void addRestaurant(RestaurantDTO restaurantData) throws Exception;
    void addTable(RestaurantTable table) throws Exception;
    void save(List<RestaurantDTO> restaurants) throws Exception;
    void updateRestaurant(Restaurant restaurant);
    void saveTables(List<RestaurantTable> tables) throws Exception;
    int reserveTable(TableReservation reservation) throws Exception;
    public void cancelReservation(ReservationCancellationRequest request) throws Exception;
    List<Restaurant> getRestaurantByType(String type);

    List<Restaurant> getRestaurantsByName(String city);
    Restaurant getRestaurantByName(String name) throws Exception;
    List<TableReservation> getReservationByUsername(String username);
    List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName, Date date) throws Exception;

    List<TableReservation> getReservationsByRestaurant(String restaurantName) throws Exception;
    Restaurant getCurrentRes(String restaurantName) throws Exception;

    List<RestaurantTable> getTablesByRestaurant(String restaurantName) throws Exception;

    List<Restaurant> getRestaurantsByManager(String managerUsername) throws Exception;
    Feedback getAverageFeedbackOfRestaurant(String restaurnatName);

    List<TableReservation> getReservationsByUserName(String userName ) throws Exception;

    List<RestaurantTable> getTablesByRestaurantName(String restaurantName) throws Exception;
    List<Restaurant> getRestaurantsByCity(String cityName);
    List<Restaurant> fetchAll();
    void delete(String restaurantName) throws Exception;
    List<Integer> getAvailableTimesByRestaurant(String restaurantName, Date date) throws Exception;
}
