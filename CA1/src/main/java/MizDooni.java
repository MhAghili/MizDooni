import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.DataBase;
import interfaces.FeedbackService;
import interfaces.RestaurantService;
import interfaces.UserService;
import models.*;
import utils.ConsoleIOHandler;
import defines.CommandType;  //?????

import java.util.Map;

public class MizDooni {
    private DataBase dataBase;
    private RestaurantService restaurantService;
    private UserService userService;

    private FeedbackService feedbackService;

    public MizDooni(DataBase dataBase, RestaurantService restaurantService, UserService userService,FeedbackService feedbackService) {
        this.dataBase = dataBase;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    public void run() throws Exception {
        var mapper = new ObjectMapper();
        var response = new Response();
        while (true) {
            try {
                var input = ConsoleIOHandler.getInput(); // Move input reading inside the loop
                switch (input.getCommand()) {
                    case CommandType.ADD_USER:
                        userService.addUser(mapper.readValue(input.getJsonData(), User.class));
                        response.setData("User added successfully.");
                        break;

                    case CommandType.ADD_RESTAURANT:
                        restaurantService.addRestaurant(mapper.readValue(input.getJsonData(), Restaurant.class));
                        response.setData("Restaurant added successfully.");
                        break;
                    case CommandType.ADD_TABLE:
                        restaurantService.addTable(mapper.readValue(input.getJsonData(), Table.class));
                        response.setData("Table added successfully.");
                        break;
                    case CommandType.RESERVE_TABLE:
                        var reservationNumber = restaurantService.reserveTable(mapper.readValue(input.getJsonData(), TableReservation.class));
                        response.setData("{\"reservationNumber\":" + reservationNumber + "}");
                        break;

                    case CommandType.CANCEL_RESERVATION:
                        restaurantService.cancelReservation(mapper.readValue(input.getJsonData(), ReservationCancellationRequest.class));
                        response.setData("Reservation canceled successfully.");
                        break;
                    case CommandType.SHOW_RESERVATION_HISTORY:
                        var reservationHistory = restaurantService.getReservationByUsername((String) mapper.readValue(input.getJsonData(), Map.class).get("username"));
                        response.setData(mapper.writeValueAsString(reservationHistory));
                        break;
                    case CommandType.SEARCH_RESTAURANTS_BY_NAME:
                        var restaurantsFilteredByName = restaurantService.getRestaurantByName((String) mapper.readValue(input.getJsonData(), Map.class).get("name"));
                        response.setData(mapper.writeValueAsString(restaurantsFilteredByName));
                        break;
                    case CommandType.SEARCH_RESTAURANTS_BY_TYPE:
                        var restaurantsFilteredByType = restaurantService.getRestaurantByType((String) mapper.readValue(input.getJsonData(), Map.class).get("name"));
                        response.setData(mapper.writeValueAsString(restaurantsFilteredByType));
                        break;
                    case CommandType.SHOW_AVAILABLE_TABLES:
                        var availableTimes = restaurantService.getAvailableTablesByRestaurant((String) mapper.readValue(input.getJsonData(), Map.class).get("restaurantName"));
                        response.setData(mapper.writeValueAsString(availableTimes));
                        break;
                    case CommandType.ADD_REVIEW:
                        feedbackService.addReview(mapper.readValue(input.getJsonData(), Feedback.class));
                        response.setData("Feedback added successfully.");
                        break;
                }
                response.setSuccess(true);
            }
            catch  (Exception ex) {
                response.setSuccess(false);
                response.setData(ex.getMessage());
            }
            ConsoleIOHandler.writeOutput(response.toString());
        }
    }


}