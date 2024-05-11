package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number")
    private int tableNumber;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "manager_username")
    private String managerUsername;

    @Column(name = "seats_number")
    private int seatsNumber;

    public RestaurantTable(int tableNumber, String restaurantName, String managerUsername, int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.managerUsername = managerUsername;
        this.seatsNumber = seatsNumber;
    }
}
