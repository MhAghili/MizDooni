package services;

import DataBase.MemoryDataBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.DataBase;
import interfaces.FeedbackService;
import interfaces.RestaurantService;
import interfaces.UserService;
import lombok.Getter;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Dictionary;
import java.util.List;


public class MizDooni {
    private static MizDooni instance = null;
    private static ObjectMapper mapper = new ObjectMapper();
    private static Response response = new Response();
    private static RestTemplate restTemplate = new RestTemplate();

    public static void fetchAndStoreData(String address) {
        var usersResponse = restTemplate.getForEntity(address + "/users", String.class).getBody();
        var restaurantsResponse = restTemplate.getForEntity(address + "/restaurants", String.class).getBody();
        var reviewsResponse = restTemplate.getForEntity(address + "/reviews", String.class).getBody();
        var tablesResponse = restTemplate.getForEntity(address + "/tables", String.class).getBody();

        try {
            var users = mapper.readValue(usersResponse, new TypeReference<List<User>>() {});
            UserServiceImplementation.getInstance().save(users);
            var restaurants = mapper.readValue(restaurantsResponse, new TypeReference<List<Restaurant>>() {});
            RestaurantServiceImpl.getInstance().save(restaurants);
            var reviews = mapper.readValue(reviewsResponse, new TypeReference<List<Feedback>>() {});
            FeedbackServiceImpl.getInstance().save(reviews);
            var tables = mapper.readValue(tablesResponse, new TypeReference<List<Table>>() {});
            RestaurantServiceImpl.getInstance().saveTables(tables);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



