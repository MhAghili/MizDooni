package utils.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.CustomDateDeserializer;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
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

}

