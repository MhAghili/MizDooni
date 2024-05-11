package exceptions;
import static defines.Errors.TIME_SLOT_ALREADY_BOOKED;
public class TimeSlotAlreadyBookedException extends Exception {
    public TimeSlotAlreadyBookedException() {
        super(TIME_SLOT_ALREADY_BOOKED);
    }
}
