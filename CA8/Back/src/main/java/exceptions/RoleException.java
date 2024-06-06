package exceptions;
import static defines.Errors.ROLE_PERMISSION;
public class RoleException extends Exception{
    public RoleException() {
        super(ROLE_PERMISSION);
    }
}
