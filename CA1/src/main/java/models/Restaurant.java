package models;

import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Getter
@Value
public class Restaurant {
    private String name;
    private String managerUsername;
    private String type;
    private Date startTime;
    private Date endTime;
    private String description;
    private Address address;
}
