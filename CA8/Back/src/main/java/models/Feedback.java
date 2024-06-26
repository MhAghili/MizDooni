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
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "restaurant_name", referencedColumnName = "name")
    private Restaurant restaurant;

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

    public Feedback( double foodRate, double serviceRate, double ambianceRate, double overallRate, String comment, User user, Restaurant restaurant) {

        this.foodRate = foodRate;
        this.serviceRate = serviceRate;
        this.ambianceRate = ambianceRate;
        this.overallRate = overallRate;
        this.comment = comment;
        this.user = user;
        this.restaurant = restaurant;
    }
}
