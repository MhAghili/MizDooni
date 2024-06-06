package exceptions;

import static defines.Errors.ADDRESS_SHOULD_CONTAINS_CITY_AND_COUNTRY;

public class AddressShouldContainsCityAndCountry extends Error{
    public AddressShouldContainsCityAndCountry() {
        super(ADDRESS_SHOULD_CONTAINS_CITY_AND_COUNTRY);
    }
}
