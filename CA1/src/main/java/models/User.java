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

    public User(UserType role, String username, String password, String email, UserAddress address) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

}