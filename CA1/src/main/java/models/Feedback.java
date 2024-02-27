package models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    private String username;
    private String restaurantName;
    private double foodRate;
    private double serviceRate;
    private double ambianceRate;
    private double overallRate;
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
