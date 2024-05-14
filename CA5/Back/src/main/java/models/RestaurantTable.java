package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Setter;

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

    @Setter
    @ManyToOne
    @JoinColumn(name = "manager_username", referencedColumnName = "username")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "restaurant_name", referencedColumnName = "name")
    private Restaurant restaurant;

    private String restaurantName;


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
