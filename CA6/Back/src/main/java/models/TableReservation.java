package models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "table_reservations")
public class TableReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationNumber;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_name", referencedColumnName = "name")
    private Restaurant restaurant;


    @Column(name = "table_number")
    private int tableNumber;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;



    @Column(name = "datetime")
    private Date datetime;

    public TableReservation(int tableNumber, Date datetime,User user, Restaurant restaurant,int numberOfPeople){
        this.tableNumber = tableNumber;
        this.datetime = datetime;
        this.user = user;
        this.restaurant = restaurant;
        this.numberOfPeople = numberOfPeople;
    }

}

