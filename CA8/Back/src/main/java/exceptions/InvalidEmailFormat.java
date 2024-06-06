package exceptions;

import static defines.Errors.INVALID_EMAIL_FORMAT;

public class InvalidEmailFormat extends Exception{
    public InvalidEmailFormat() {
        super(INVALID_EMAIL_FORMAT);
    }
}
