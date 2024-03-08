package services;

import defines.AllowedRateRange;
import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.FeedbackService;
import models.Feedback;
import static defines.AllowedRateRange.*;
import DataBase.*;

import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {
    private static FeedbackService instance = null;
    private DataBase dataBase;

    private FeedbackServiceImpl(){
        this.dataBase = MemoryDataBase.getInstance();
    }

    public static FeedbackService getInstance() {
        if (instance == null)
            instance = new FeedbackServiceImpl();
        return instance;
    }
    @Override
    public void addReview(Feedback feedback) throws Exception{
        var user = dataBase.getUsers()
                .filter(u -> u.getUsername().equals(feedback.getUsername()))
                .findFirst()
                .orElseThrow(UserNotFound::new);

        if (user.getRole() == UserType.manager) {
            throw new RoleException();
        }

        var restaurant = dataBase.getRestaurants()
                .filter(r -> r.getName().equals(feedback.getRestaurantName()))
                .findFirst()
                .orElseThrow(RestaurantNotFound::new);

        if((feedback.getServiceRate() < MIN_RATE || feedback.getServiceRate() > MAX_RATE) ||
                (feedback.getFoodRate() < MIN_RATE || feedback.getFoodRate() > MAX_RATE) ||
                (feedback.getAmbianceRate() < MIN_RATE || feedback.getAmbianceRate() > MAX_RATE) ||
                (feedback.getOverallRate() < MIN_RATE || feedback.getOverallRate() > MAX_RATE))
        {

            throw new AllowedRangeException();
        }

        dataBase.saveFeedback(feedback);
    }

    @Override
    public List<Feedback> getReviewsByRestaurantName(String restaurantName) throws Exception {
        return dataBase.getFeedbacks().filter(f -> f.getRestaurantName().equals(restaurantName)).toList();
    }

}
