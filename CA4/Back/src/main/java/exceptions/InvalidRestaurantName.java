package exceptions;

import static defines.Errors.INVALID_RESTAURANT_NAME;

public class InvalidRestaurantName extends Exception{
    public InvalidRestaurantName() {
        super(INVALID_RESTAURANT_NAME);
    }
}
