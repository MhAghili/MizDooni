package exceptions;

import static defines.Errors.ADDRESS_SHOULD_CONTAINS_CITY_AND_COUNTRY_AND_STREET;

public class AddressShouldContainsCityAndCountryAndStreet extends Exception{
    public AddressShouldContainsCityAndCountryAndStreet() {
        super(ADDRESS_SHOULD_CONTAINS_CITY_AND_COUNTRY_AND_STREET);
    }
}
