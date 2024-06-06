package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import utils.DTO.FeedbackDTO;
import utils.DTO.RestaurantDTO;
import utils.DTO.RestaurantTableDTO;
import utils.Response;

import java.util.List;

@ComponentScan
public class MizDooni {

    private static MizDooni instance ;

    private MizDooni() {
        fetchAndStoreData(("http://91.107.137.117:55/"));

    }

    public static MizDooni getInstance() {
        if (instance == null) {
            instance = new MizDooni();
        }
        return instance;
    }

    private static ObjectMapper mapper = new ObjectMapper();
    private static Response response = new Response();
    private static RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStoreData(String address) {
        var usersResponse = restTemplate.getForEntity(address + "/users", String.class).getBody();
        var restaurantsResponse = restTemplate.getForEntity(address + "/restaurants", String.class).getBody();
        var reviewsResponse = restTemplate.getForEntity(address + "/reviews", String.class).getBody();
        var tablesResponse = restTemplate.getForEntity(address + "/tables", String.class).getBody();

        try {
            var users = mapper.readValue(usersResponse, new TypeReference<List<User>>() {});
            UserServiceImplementation.getInstance().save(users);
            var restaurants = mapper.readValue(restaurantsResponse, new TypeReference<List<RestaurantDTO>>() {});
            RestaurantServiceImpl.getInstance().save(restaurants);
            var reviews = mapper.readValue(reviewsResponse, new TypeReference<List<FeedbackDTO>>() {});
            FeedbackServiceImpl.getInstance().save(reviews);
            var tables = mapper.readValue(tablesResponse, new TypeReference<List<RestaurantTableDTO>>() {});
            RestaurantServiceImpl.getInstance().saveTables(tables);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



