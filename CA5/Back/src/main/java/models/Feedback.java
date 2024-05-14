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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "username")
    private User user_name;

    @Setter
    @ManyToOne
    @JoinColumn(name = "restaurant_name", referencedColumnName = "name")
    private Restaurant restaurant_name;

    private String username;

    private String restaurantName;

    @Column(name = "food_rate")
    private double foodRate;

    @Column(name = "service_rate")
    private double serviceRate;

    @Column(name = "ambiance_rate")
    private double ambianceRate;

    @Column(name = "overall_rate")
    private double overallRate;

    @Column(name = "comment")
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
