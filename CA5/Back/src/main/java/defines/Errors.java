package defines;

public class Errors {
    public static final String USERNAME_ALREADY_TAKEN = "The username is already taken.";
    public static final String EMAIL_ALREADY_TAKEN = "The email is already taken.";
    public static final String INVALID_USER_TYPE = "User Type should be manager or client.";
    public static final String INVALID_EMAIL_FORMAT = "The email is in invalid format.";
    public static final String INVALID_USERNAME_FORMAT = "The username is in invalid format";
    public static final String ADDRESS_SHOULD_CONTAINS_CITY_AND_COUNTRY = "The address should contains city and country";
    public static final String RESTAURANT_NAME_ALREADY_TAKEN = "The restaurant name is already taken.";
    public static final String INVALID_MANAGER_USERNAME = "The manager username is invalid";
    public static final String TIME_OF_RESTAURANT_SHOULD_BE_ROUND = "The start time and end time of restaurant should be round hours";
    public static final String ADDRESS_SHOULD_CONTAINS_CITY_AND_COUNTRY_AND_STREET = "The address should contains city and country and street";
    public static final String TABLE_NUMBER_ALREADY_TAKEN = "The table number is already taken in this restaurant";
    public static final String INVALID_RESTAURANT_NAME = "Invalid restaurant name";
    public static final String INVALID_RESERVATION_NUMBER = "Invalid Reservation number";
    public static final String CANNOT_CANCEL_RESERVATION_BECAUSE_OF_DATE = "The reservation cannot be cancelled because of date";
    public static final String USER_NOT_FOUND = "The user is not found";
    public static final String PAST_DATE_TIME = "The selected time is in the past";
    public static final String OUTSIDE_BUSINESS_HOURS = "The selected time is outside the restaurant's business hours";
    public static final String TABLE_NOT_FOUND = "The table is not found in the restaurant";
    public static final String TIME_SLOT_ALREADY_BOOKED = "The selected time slot is already booked";
    public static final String ROLE_PERMISSION = "The user doesn't have permission to do this action";
    public static final String OUT_OF_RATE_RANGE = "The rate is out of range";
    public static final String RESTAURANT_NOT_FOUND = "The restaurant is not found";
    public static final String MUST_HAVE_PAST_RESERVATION_FOR_ADD_FEEDBACK = "To add feedback, you must have a reservation for restaurant and it must be past the reservation date";
    public static final String INVALID_PASSWORD = "The password is invalid";
}
