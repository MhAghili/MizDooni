package models;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class Table {
    private int number;
    private String restaurantName;
    private String managerUsername;
    private int seatsNumber;
}
