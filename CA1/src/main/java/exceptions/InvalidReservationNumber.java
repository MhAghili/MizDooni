package exceptions;

import static defines.Errors.INVALID_RESERVATION_NUMBER;

public class InvalidReservationNumber extends Exception{
    public InvalidReservationNumber() {
        super(INVALID_RESERVATION_NUMBER);
    }
}
