package utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
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
