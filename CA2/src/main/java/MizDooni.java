import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.DataBase;
import interfaces.FeedbackService;
import interfaces.RestaurantService;
import interfaces.UserService;
import models.*;
import utils.ConsoleIOHandler;
import defines.CommandType;

import java.io.File;
import java.util.Map;

public class MizDooni {
    private DataBase dataBase;
    private RestaurantService restaurantService;
    private UserService userService;

    private FeedbackService feedbackService;

    private ObjectMapper mapper = new ObjectMapper();
    public MizDooni(DataBase dataBase, RestaurantService restaurantService, UserService userService,FeedbackService feedbackService) {
        this.dataBase = dataBase;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    public void ReadFromJsonFile(File file) throws Exception {
        var jsonData = mapper.readValue(file, InputData.class);
        var response = new Response();
        for (Command command : jsonData.getCommands()) {
            try {
                switch (command.getCommand()) {
                    case CommandType.ADD_USER:
                        User user = mapper.convertValue(command.getJsonData(), User.class);
                        userService.addUser(user);
                        response.setData("User added successfully.");
                        break;
                    case CommandType.ADD_RESTAURANT:
                        Restaurant restaurant = mapper.convertValue(command.getJsonData(), Restaurant.class);
                        restaurantService.addRestaurant(restaurant);
                        response.setData("Restaurant added successfully.");
                        break;
                    case CommandType.ADD_TABLE:
                        Table table = mapper.convertValue(command.getJsonData(), Table.class);
                        restaurantService.addTable(table);
                        response.setData("Table added successfully.");
                        break;
                    case CommandType.RESERVE_TABLE:
                        TableReservation tableReservation = mapper.convertValue(command.getJsonData(), TableReservation.class);
                        var reservationNumber = restaurantService.reserveTable(tableReservation);
                        response.setData("{\"reservationNumber\":" + reservationNumber + "}");
                        break;
                    case CommandType.CANCEL_RESERVATION:
                        ReservationCancellationRequest reservationCancellationRequest = mapper.convertValue(command.getJsonData(), ReservationCancellationRequest.class);
                        restaurantService.cancelReservation(reservationCancellationRequest);
                        response.setData("Reservation canceled successfully.");
                        break;
                    case CommandType.SHOW_RESERVATION_HISTORY:
                        Map reservationHistory = (Map) mapper.convertValue(command.getJsonData(), Map.class).get("username");
                        response.setData(mapper.writeValueAsString(reservationHistory));
                        break;
                    case CommandType.SEARCH_RESTAURANTS_BY_NAME:
                        var restaurantsFilteredByName = (Map) mapper.convertValue(command.getJsonData(), Map.class).get("name");
                        response.setData(mapper.writeValueAsString(restaurantsFilteredByName));
                        break;
                    case CommandType.SEARCH_RESTAURANTS_BY_TYPE:
                        var restaurantsFilteredByType = restaurantService.getRestaurantByType((String) command.getJsonData().get("type"));
                        response.setData(mapper.writeValueAsString(restaurantsFilteredByType));
                        break;
                    case CommandType.SHOW_AVAILABLE_TABLES:
                        var availableTimes = restaurantService.getAvailableTablesByRestaurant((String) command.getJsonData().get("restaurantName"));
                        response.setData(mapper.writeValueAsString(availableTimes));
                        break;
                    case CommandType.ADD_REVIEW:
                        var feedBack = mapper.convertValue(command.getJsonData(), Feedback.class);
                        feedbackService.addReview(feedBack);
                        response.setData("Review added successfully.");
                        break;
                }
                response.setSuccess(true);
            } catch (Exception ex) {
                response.setSuccess(false);
                response.setData(ex.getMessage());
            }
            ConsoleIOHandler.writeOutput(response.toString());
        }
    }

    public void run() throws Exception {
        ReadFromJsonFile(new File("src/main/java/input.json"));
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
                        var restaurantsFilteredByType = restaurantService.getRestaurantByType((String) mapper.readValue(input.getJsonData(), Map.class).get("type"));
                        response.setData(mapper.writeValueAsString(restaurantsFilteredByType));
                        break;
                    case CommandType.SHOW_AVAILABLE_TABLES:
                        var availableTimes = restaurantService.getAvailableTablesByRestaurant((String) mapper.readValue(input.getJsonData(), Map.class).get("restaurantName"));
                        response.setData(mapper.writeValueAsString(availableTimes));
                        break;
                    case CommandType.ADD_REVIEW:
                        feedbackService.addReview(mapper.readValue(input.getJsonData(), Feedback.class));
                        response.setData("Review added successfully.");
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