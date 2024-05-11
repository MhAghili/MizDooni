package exceptions;

import static defines.Errors.USER_NOT_FOUND;

public class UserNotFound extends Exception {
    public UserNotFound() {
        super(USER_NOT_FOUND);
    }
}
