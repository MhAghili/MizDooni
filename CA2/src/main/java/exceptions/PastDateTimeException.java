package exceptions;

import static defines.Errors.PAST_DATE_TIME;
public class PastDateTimeException extends Exception{
    public PastDateTimeException() {
        super(PAST_DATE_TIME);
    }
}
