package exceptions;

import static defines.Errors.CANNOT_CANCEL_RESERVATION_BECAUSE_OF_DATE;

public class CannotCancelReservationBecauseOfDate extends Exception {
    public CannotCancelReservationBecauseOfDate() {
        super(CANNOT_CANCEL_RESERVATION_BECAUSE_OF_DATE);
    }
}
