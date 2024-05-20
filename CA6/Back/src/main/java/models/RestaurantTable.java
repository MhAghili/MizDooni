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


    @Column(name = "seats_number")
    private int seatsNumber;

    public RestaurantTable(int tableNumber, int seatsNumber, User user, Restaurant restaurant) {
        this.tableNumber = tableNumber;
        this.seatsNumber = seatsNumber;
        this.user = user;
        this.restaurant = restaurant;

    }
}
