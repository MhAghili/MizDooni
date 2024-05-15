package utils.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class RestaurantTableDTO {

    private int tableNumber;


    private String restaurantName;


    private String managerUsername;

    private int seatsNumber;

    public RestaurantTableDTO(int tableNumber, String restaurantName, String managerUsername, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.managerUsername = managerUsername;
        this.seatsNumber = seatsNumber;
    }
}
