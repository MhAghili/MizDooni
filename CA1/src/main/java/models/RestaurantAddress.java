package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@Value
@NoArgsConstructor(force = true)
public class RestaurantAddress {
    private String country;
    private String city;
    private String street;

    public RestaurantAddress(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
