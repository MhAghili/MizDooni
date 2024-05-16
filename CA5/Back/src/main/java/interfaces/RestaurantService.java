package interfaces;

import models.*;
import models.TableReservationDTO;
import utils.AvailableTableInfo;
import utils.DTO.FeedbackDTO;
import utils.DTO.RestaurantTableDTO;
import utils.ReservationCancellationRequest;
import utils.DTO.RestaurantDTO;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface RestaurantService {
    void addRestaurant(RestaurantDTO restaurantData) throws Exception;

    void addTable(RestaurantTableDTO table) throws Exception;

    void save(List<RestaurantDTO> restaurants) throws Exception;

    void saveTables(List<RestaurantTableDTO> tables) throws Exception;

    //    int reserveTable(TableReservationDTO reservation) throws Exception;
    void cancelReservation(ReservationCancellationRequest request) throws Exception;

    List<Restaurant> getRestaurantByType(String type);

    List<Restaurant> getRestaurantsByName(String city);

    Restaurant getRestaurantByName(String name) throws Exception;

    List<TableReservation> getReservationByUsername(String username);
//    List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName, Date date) throws Exception;

    List<TableReservation> getReservationsByRestaurant(String restaurantName) throws Exception;

    List<RestaurantTable> getTablesByRestaurant(String restaurantName) throws Exception;

    List<Restaurant> getRestaurantsByManager(String managerUsername) throws Exception;

    FeedbackDTO getAverageFeedbackOfRestaurant(String restaurnatName);

    List<TableReservation> getReservationsByUserName(String userName) throws Exception;

    List<RestaurantTable> getTablesByRestaurantName(String restaurantName) throws Exception;

    List<Restaurant> fetchAll();

    void delete(String restaurantName) throws Exception;
//    List<Integer> getAvailableTimesByRestaurant(String restaurantName, Date date) throws Exception;

    List<AvailableTableInfo> showAvailableTimes(String restaurantName, LocalDate date) throws Exception;

    void reserveTable(TableReservationDTO reservation) throws Exception;
}