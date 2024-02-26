package models;

import enums.UserType;
import lombok.Getter;
import lombok.Value;
import lombok.NoArgsConstructor;

@Getter
@Value
@NoArgsConstructor(force = true)
public class User {
    private UserType role;
    private String username;
    private String password;
    private String email;
    private UserAddress address;

}