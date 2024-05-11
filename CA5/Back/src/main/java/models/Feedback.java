package models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id // Indicates the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-increment strategy for primary key
    private Long id; // Assuming you have an ID field in your Feedback table

    @Column(name = "username") // Specifies column name in the database
    private String username;

    @Column(name = "restaurant_name") // Specifies column name in the database
    private String restaurantName;

    @Column(name = "food_rate") // Specifies column name in the database
    private double foodRate;

    @Column(name = "service_rate") // Specifies column name in the database
    private double serviceRate;

    @Column(name = "ambiance_rate") // Specifies column name in the database
    private double ambianceRate;

    @Column(name = "overall_rate") // Specifies column name in the database
    private double overallRate;

    @Column(name = "comment") // Specifies column name in the database
    private String comment;

    public Feedback(String username, String restaurantName, double foodRate, double serviceRate, double ambianceRate, double overallRate, String comment) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.foodRate = foodRate;
        this.serviceRate = serviceRate;
        this.ambianceRate = ambianceRate;
        this.overallRate = overallRate;
        this.comment = comment;
    }
}
