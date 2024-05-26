package exceptions;

import static defines.Errors.RESTAURANT_NAME_ALREADY_TAKEN;

public class RestaurantNameAlreadyTaken extends Exception{
    public RestaurantNameAlreadyTaken() {
        super(RESTAURANT_NAME_ALREADY_TAKEN);
    }
}
