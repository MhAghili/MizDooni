package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.CustomDateDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
public class TableReservation {
    private int number; // Unique reservation number
    private String username;
    private String restaurantName;
    private int tableNumber;
    private Integer numberOfPeople;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date datetime;

    public TableReservation(int reservationNumber, String username, String restaurantName, int tableNumber, Date datetime) {
        this.number = reservationNumber;
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "reservationNumber: " + number
              + "restaurantName: " + restaurantName
              + "tableNumber: " + tableNumber
              + "datetime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(datetime);
    }
}

