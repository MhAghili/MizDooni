package models;

import enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private UserType role;

    @Setter
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Embedded
    private UserAddress address;


}