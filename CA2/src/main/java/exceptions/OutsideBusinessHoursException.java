package exceptions;
import static defines.Errors.OUTSIDE_BUSINESS_HOURS;
public class OutsideBusinessHoursException extends Exception {
    public OutsideBusinessHoursException() {
        super(OUTSIDE_BUSINESS_HOURS);
    }
}
