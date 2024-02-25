import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.DataBase;
import interfaces.RestaurantService;
import interfaces.UserService;
import models.User;
import utils.ConsoleIOHandler;
import defines.CommandType;

public class MizDooni {
    private DataBase dataBase;
    private RestaurantService restaurantService;
    private UserService userService;

    public MizDooni(DataBase dataBase, RestaurantService restaurantService, UserService userService) {
        this.dataBase = dataBase;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public void run() {
        while (true) {
            var input = ConsoleIOHandler.getInput();
            var mapper = new ObjectMapper();
            switch (input.getCommand()) {
                case CommandType.ADD_USER:
                    userService.addUser(mapper.readValue(input.getJsonData(), User.class));
                    break;
            }
        }
    }



}