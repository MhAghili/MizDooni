package models;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class Address {
    private String country;
    private String city;
    private String street;
}
