package exceptions;

import static defines.Errors.INVALID_USERNAME_FORMAT;

public class InvalidUsernameFormat extends Error{
    public InvalidUsernameFormat() {
        super(INVALID_USERNAME_FORMAT);
    }
}
