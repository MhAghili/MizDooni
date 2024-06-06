package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.UserType;
import interfaces.UserService;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import services.OauthService;
import utils.JWTUtil;
import services.UserServiceImplementation;

import utils.LoginRequest;

import java.util.Map;

@RestController
public class LoginController {
    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserService userService;

    private final OauthService oathService;
    public LoginController() {
        userService = UserServiceImplementation.getInstance();
        oathService = new OauthService();
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
            userService.addUser(user);
            String token = JWTUtil.generateToken(user.getUsername());
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("signup successful");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, String>> GoogleCallback(@RequestBody Map<String, String> body)
    {
        String code = body.get("code");

        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://oauth2.googleapis.com/token?client_id=" + clientId +
                "&client_secret=" + clientSecret + "&code=" + code +
                "&grant_type=authorization_code&redirect_uri=" + redirectUri;

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getBody());
                String accessToken = jsonNode.get("access_token").asText();

                String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(accessToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
                if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                    JsonNode userInfo = mapper.readTree(userInfoResponse.getBody());
                    String email = userInfo.get("email").asText();
                    String username = userInfo.get("name").asText().replaceAll("\\s", "");

                    User user = userService.getUserByName(username);
                    if (user == null) {
                        user = new User();
                        user.setUsername(username);
                        user.setEmail(email);
                        user.setRole(UserType.client);
                        oathService.addUser(user);
                    }

                    String jwtToken = JWTUtil.generateToken(username);
                    Map<String, String> responseBody = Map.of(
                            "username", username,
                            "token", jwtToken
                    );

                    return ResponseEntity.ok(responseBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error retrieving user info"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid code"));
    }
}

