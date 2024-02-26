package exceptions;
import static defines.Errors.TABLE_NOT_FOUND;
public class TableNotFoundException extends Exception{
    public TableNotFoundException() {
        super(TABLE_NOT_FOUND);
    }
}
