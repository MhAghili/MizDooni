package controllers;

import interfaces.UserService;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.JWTUtil;
import services.UserServiceImplementation;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import utils.LoginRequest;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserService service;
    public LoginController() {
        service = UserServiceImplementation.getInstance();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = JWTUtil.generateToken(username);
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            service.addUser(user);
            String token = JWTUtil.generateToken(user.getUsername());
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("signup successful");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

}
