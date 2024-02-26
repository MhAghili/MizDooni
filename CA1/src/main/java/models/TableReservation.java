package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.CustomDateDeserializer;

import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
public class TableReservation {
    private int number; // Unique reservation number
    private String username;
    private String restaurantName;
    private int tableNumber;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dateTime;

    public TableReservation(int reservationNumber, String username, String restaurantName, int tableNumber, Date dateTime) {
        this.number = reservationNumber;
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.dateTime = dateTime;
    }
}

