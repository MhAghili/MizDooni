package services;

import defines.AllowedRateRange;
import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.FeedbackService;
import models.Feedback;
import static defines.AllowedRateRange.*;

public class FeedbackServiceImpl implements FeedbackService {

    private DataBase dataBase;

    public FeedbackServiceImpl(DataBase dataBase){
        this.dataBase = dataBase;
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
}
