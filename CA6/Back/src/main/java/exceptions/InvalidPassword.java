package exceptions;
import static defines.Errors.INVALID_PASSWORD;
public class InvalidPassword extends Exception {
    public InvalidPassword() {
        super(INVALID_PASSWORD);
    }
}
