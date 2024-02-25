package models;

import lombok.Getter;

import java.util.Date;

@Getter
public class TableReservation {
    private int number;
    private String username;
    private String restaurantName;
    private int tableNumber;
    private Date dateTime;

    public TableReservation(String username, String restaurantName, int tableNumber, Date dateTime) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.dateTime = dateTime;
    }
}
