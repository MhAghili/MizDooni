package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.CustomDateDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
public class TableReservationDTO {

    private int number; // Unique reservation number


    private String username;

    private String restaurantName;

    private int tableNumber;

    private Integer numberOfPeople;

    private Date datetime;

    public TableReservationDTO(int reservationNumber, String username, String restaurantName, int tableNumber, Date datetime,User user, Restaurant restaurant, int numberOfPeople){
        this.number = reservationNumber;
        this.username = username;
        this.restaurantName = restaurantName;
        this.numberOfPeople = numberOfPeople;
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

