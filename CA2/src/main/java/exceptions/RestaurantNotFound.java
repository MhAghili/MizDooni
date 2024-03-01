package exceptions;
import static defines.Errors.RESTAURANT_NOT_FOUND;
public class RestaurantNotFound extends Exception{
    public RestaurantNotFound(){
        super(RESTAURANT_NOT_FOUND);
    }
}
