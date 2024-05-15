package utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import models.RestaurantAddress;
import utils.CustomTimeDeserializer;

import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
public class RestaurantDTO {

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

    private String image;


}
