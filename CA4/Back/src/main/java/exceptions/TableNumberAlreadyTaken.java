package exceptions;

import static defines.Errors.TABLE_NUMBER_ALREADY_TAKEN;

public class TableNumberAlreadyTaken extends Exception{
    public TableNumberAlreadyTaken() {
        super(TABLE_NUMBER_ALREADY_TAKEN);
    }
}
