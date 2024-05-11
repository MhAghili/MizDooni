package exceptions;

import static defines.Errors.MUST_HAVE_PAST_RESERVATION_FOR_ADD_FEEDBACK;

public class MustHavePastReservationForAddFeedback extends Exception {
    public MustHavePastReservationForAddFeedback() {
        super(MUST_HAVE_PAST_RESERVATION_FOR_ADD_FEEDBACK);
    }
}
