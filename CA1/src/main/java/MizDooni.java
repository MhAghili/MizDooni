import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.DataBase;
import interfaces.RestaurantService;
import interfaces.UserService;
import models.Restaurant;
import models.Table;
import models.TableReservation;
import models.User;
import utils.ConsoleIOHandler;
import defines.CommandType;  //?????

public class MizDooni {
    private DataBase dataBase;
    private RestaurantService restaurantService;
    private UserService userService;

    public MizDooni(DataBase dataBase, RestaurantService restaurantService, UserService userService) {
        this.dataBase = dataBase;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public void run() throws Exception {
        while (true) {
            var input = ConsoleIOHandler.getInput();
            var mapper = new ObjectMapper();
            switch (input.getCommand()) {
                case CommandType.ADD_USER:
                    userService.addUser(mapper.readValue(input.getJsonData(), User.class));
                    break;
            }
            switch (input.getCommand()) {
                case CommandType.ADD_RESTAURANT:
                    restaurantService.addRestaurant(mapper.readValue(input.getJsonData(), Restaurant.class));
                    break;
            }
            switch (input.getCommand()) {
                case CommandType.ADD_TABLE:
                    restaurantService.addTable(mapper.readValue(input.getJsonData(), Table.class));
                    break;
            }
            switch (input.getCommand()) {
                case CommandType.RESERVE_TABLE:
                    restaurantService.reserveTable(mapper.readValue(input.getJsonData(), TableReservation.class));
                    break;
            }

        }
    }


}