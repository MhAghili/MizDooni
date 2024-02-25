package models;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

public class Review {
    private String username;
    private String restaurantName;
    private float foodRate;
    private float serviceRate;
    private float ambianceRate;
    private float overallRate;
    private String comment;
    private Date creationDate;

    public Review(String username, String restaurantName, float foodRate, float serviceRate, float ambianceRate, float overallRate, String comment) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.foodRate = foodRate;
        this.serviceRate = serviceRate;
        this.ambianceRate = ambianceRate;
        this.overallRate = overallRate;
        this.comment = comment;
        // TODO
        //this.creationDate = ;
    }
}
