package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@Value
@NoArgsConstructor(force = true)
public class UserAddress {
    private String country;
    private String city;

    public UserAddress(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
