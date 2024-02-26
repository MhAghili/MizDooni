package models;

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

    @JsonDeserialize(using = CustomTimeDeserializer.class)
    private Date startTime;

    @JsonDeserialize(using = CustomTimeDeserializer.class)
    private Date endTime;

    private String description;
    private RestaurantAddress address;
}
