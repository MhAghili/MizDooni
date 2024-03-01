package exceptions;

import static defines.Errors.INVALID_MANAGER_USERNAME;

public class InvalidManagerUsername extends Exception{
    public InvalidManagerUsername() {
        super(INVALID_MANAGER_USERNAME);
    }
}
