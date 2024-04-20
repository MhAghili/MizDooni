package exceptions;

import static defines.Errors.TIME_OF_RESTAURANT_SHOULD_BE_ROUND;

public class TimeOfRestaurantShouldBeRound extends Exception {
    public TimeOfRestaurantShouldBeRound() {
        super(TIME_OF_RESTAURANT_SHOULD_BE_ROUND);
    }
}
