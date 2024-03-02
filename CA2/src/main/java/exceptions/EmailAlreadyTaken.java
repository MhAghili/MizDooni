package exceptions;

import static defines.Errors.EMAIL_ALREADY_TAKEN;

public class EmailAlreadyTaken extends Exception {
    public EmailAlreadyTaken() {
        super(EMAIL_ALREADY_TAKEN);
    }
}
