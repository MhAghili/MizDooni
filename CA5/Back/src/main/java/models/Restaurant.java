package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import utils.CustomTimeDeserializer;

import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "retaurants")
public class Restaurant {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "manager_username")
    private String managerUsername;

    @Column(name = "type")
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    @JsonDeserialize(using = CustomTimeDeserializer.class)
    @Column(name = "start_time")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    @JsonDeserialize(using = CustomTimeDeserializer.class)
    @Column(name = "end_time")
    private Date endTime;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Embedded
    private RestaurantAddress address;

    @Column(name = "image")
    private String image;

    public Restaurant(String name, String managerUsername, String type, Date startTime, Date endTime, String description, RestaurantAddress address, String image) {
        this.name = name;
        this.managerUsername = managerUsername;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.image = image;
    }
}
