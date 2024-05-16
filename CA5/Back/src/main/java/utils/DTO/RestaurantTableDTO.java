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


}
