package models;

import enums.UserType;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class User {
    private UserType userType;
    private String username;
    private String password;
    private String email;
    private Address address;
}