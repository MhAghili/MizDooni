package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import utils.CustomTimeDeserializer;

import java.util.Date;

@Getter
@Value
@NoArgsConstructor(force = true)
public class Restaurant {
    private String name;
    private String managerUsername;
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    @JsonDeserialize(using = CustomTimeDeserializer.class)
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    @JsonDeserialize(using = CustomTimeDeserializer.class)
    private Date endTime;

    private String description;
    private RestaurantAddress address;

    public Restaurant(String name, String managerUsername, String type, Date startTime, Date endTime, String description, RestaurantAddress address) {
        this.name = name;
        this.managerUsername = managerUsername;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
    }
}
