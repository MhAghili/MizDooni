package exceptions;

import static defines.Errors.INVALID_USER_TYPE;

public class InvalidUserType extends Error {
    public InvalidUserType() {
        super(INVALID_USER_TYPE);
    }
}
