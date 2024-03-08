package application;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.UserType;
import interfaces.DataBase;
import interfaces.FeedbackService;
import interfaces.RestaurantService;
import interfaces.UserService;
import lombok.Getter;
import models.*;
import services.FeedbackServiceImpl;
import services.RestaurantServiceImpl;
import services.UserServiceImplementation;
import utils.Command;
import utils.ConsoleIOHandler;
import defines.CommandType;
import DataBase.*;
import utils.InputData;
import utils.ReservationCancellationRequest;

import java.io.File;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;


public class MizDooni {
    private static MizDooni instance = null;
    @Getter
    private DataBase dataBase;
    @Getter
    private RestaurantService restaurantService;
    @Getter
    private UserService userService;
    @Getter
    private FeedbackService feedbackService;

    private ObjectMapper mapper = new ObjectMapper();

    private Response response = new Response();
    private MizDooni(DataBase dataBase, RestaurantService restaurantService, UserService userService,FeedbackService feedbackService) {
        this.dataBase = dataBase;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    public static MizDooni getInstance() {
        if (instance == null)
            instance = new MizDooni(MemoryDataBase.getInstance(), RestaurantServiceImpl.getInstance(), UserServiceImplementation.getInstance(), FeedbackServiceImpl.getInstance());
        return instance;
    }
    public void ReadFromJsonFile(File file) throws Exception {
        var jsonData = mapper.readValue(file, InputData.class);
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
//        ReadFromJsonFile(new File("input.json"));
        this.dataBase.saveUser(new User(UserType.manager, "user1", "1234","user1@gmail.com",new UserAddress("Iran","Tehran")));
        this.dataBase.saveUser(new User(UserType.client, "user2", "12345", "user12@gmail.com", new UserAddress("Iran", "Tehran")));
        this.dataBase.saveRestaurant(new Restaurant("restaurant1", "user1", "Iranian", new Date(2014, 02, 11 , 8 , 0), new Date(2014, 02, 11 ,  23, 0), "Open seven days a week", new RestaurantAddress("Iran", "Tehran", "North Kargar")));
        this.dataBase.saveTable(new Table(1, "restaurant1", "user1", 4));
        this.dataBase.saveTable(new Table(2, "restaurant1", "user1", 4));
        this.dataBase.saveFeedback(new Feedback("user2", "restaurant1", 4.4, 3.3, 2.5, 3.5, "good"));
        this.dataBase.saveFeedback(new Feedback("user2", "restaurant1", 3, 4, 4, 3, "not bad"));
    }
    }



