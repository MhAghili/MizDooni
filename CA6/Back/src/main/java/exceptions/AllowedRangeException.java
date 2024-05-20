package exceptions;
import static defines.Errors.OUT_OF_RATE_RANGE;
public class AllowedRangeException extends Exception{
    public AllowedRangeException() {
        super(OUT_OF_RATE_RANGE);
    }
}
