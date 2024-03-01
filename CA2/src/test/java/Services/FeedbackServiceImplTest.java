package Services;

import enums.UserType;
import models.*;
import exceptions.*;
import org.junit.jupiter.api.AfterEach;
import services.FeedbackServiceImpl;
import interfaces.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DataBase.MemoryDataBase;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class FeedbackServiceImplTest {
    private FeedbackServiceImpl feedbackService;
    private DataBase dataBase;

    @BeforeEach
    void setUp() {
        dataBase = new MemoryDataBase();
        feedbackService = new FeedbackServiceImpl(dataBase);
        dataBase.saveUser(new User( UserType.manager, "user1", "1234", "email@yahoo.com", new UserAddress("hh","Tehran")));
        dataBase.saveUser(new User( UserType.client, "user2", "1234", "email@yahoo.com", new UserAddress("hh","Tehran")));
        dataBase.saveRestaurant(new Restaurant( "restaurant1", "manager", "type", new Date("2024/3/12 12:00:00"), new Date("2024/9/12 19:00:00"), "description", new RestaurantAddress("hh","Tehran","Kargar")));
    }

    @Test
    public void addFeedback_successfulFeedback() throws Exception {
        Feedback feedback = new Feedback("user2", "restaurant1", 4.5,3,4.5 ,4,"good");
        feedbackService.addReview(feedback);
        assertEquals(1, dataBase.getFeedbacks().count());
    }

    @Test
    public void addFeedback_managerRoleAttempt() {
        Feedback feedback = new Feedback("user1", "restaurant1", 4.5,3,4.5 ,4,"good");
        assertThrows(RoleException.class, () -> feedbackService.addReview(feedback));
    }

    @Test
    public void restaurantNotFound() {
        Feedback feedback = new Feedback("user2", "restaurant2", 4.5,3,4.5 ,4,"good");
        assertThrows(RestaurantNotFound.class, () -> feedbackService.addReview(feedback));
    }

    @Test
    public void invalidRate() {
        Feedback feedback = new Feedback("user2", "restaurant1", 5.5,3,4.5 ,5,"good");
        assertThrows(AllowedRangeException.class, () -> feedbackService.addReview(feedback));
    }

    @Test
    public void userNotFound() {
        Feedback feedback = new Feedback("user3", "restaurant1", 4.5,3,4.5 ,4,"good");
        assertThrows(UserNotFound.class, () -> feedbackService.addReview(feedback));
    }

    @AfterEach
    void tearDown() {
        dataBase = null;
        feedbackService = null;
    }
}
