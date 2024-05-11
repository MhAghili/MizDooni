package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.CustomDateDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "table_reservations")
public class TableReservation {
    @Id
    @Column(name = "number")
    private int number; // Unique reservation number

    @Column(name = "username")
    private String username;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "table_number")
    private int tableNumber;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @JsonDeserialize(using = CustomDateDeserializer.class)

    @Column(name = "datetime")
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

