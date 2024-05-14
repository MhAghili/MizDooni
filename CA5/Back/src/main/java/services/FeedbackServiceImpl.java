package services;

import defines.AllowedRateRange;
import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.FeedbackService;
import models.Feedback;
import static defines.AllowedRateRange.*;
import DataBase.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
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

//        if (!dataBase
//                .getReservations()
//                .anyMatch(i -> i.getUsername().equals(feedback.getUsername()) && i.getRestaurantName().equals(feedback.getRestaurantName())))
////                && i.getDatetime().before(new Date())))  // not work
//        {
//            throw new MustHavePastReservationForAddFeedback();
//        }


        if((feedback.getServiceRate() < MIN_RATE || feedback.getServiceRate() > MAX_RATE) ||
                (feedback.getFoodRate() < MIN_RATE || feedback.getFoodRate() > MAX_RATE) ||
                (feedback.getAmbianceRate() < MIN_RATE || feedback.getAmbianceRate() > MAX_RATE) ||
                (feedback.getOverallRate() < MIN_RATE || feedback.getOverallRate() > MAX_RATE))
        {

            throw new AllowedRangeException();
        }


        var previousFeedBack =
                dataBase
                    .getFeedbacks()
                    .filter(i -> i.getUsername().equals(feedback.getUsername()) && i.getRestaurantName().equals(feedback.getRestaurantName()))
                        .findFirst().orElse(null);
        if (previousFeedBack != null) {
            dataBase.deleteFeedback(previousFeedBack);
        }

        feedback.setRestaurant_name(restaurant);
        feedback.setUser_name(user);
        dataBase.saveFeedback(feedback);
    }

    @Override
    public void save(List<Feedback> feedbacks) throws Exception {
//        for (var feedback : feedbacks) {
//            var user = dataBase.getUsers()
//                    .filter(u -> u.getUsername().equals(feedback.getUsername()))
//                    .findFirst()
//                    .orElseThrow(UserNotFound::new);
//            var restaurant = dataBase.getRestaurants()
//                    .filter(r -> r.getName().equals(feedback.getRestaurantName()))
//                    .findFirst()
//                    .orElseThrow(RestaurantNotFound::new);
//
//            feedback.setRestaurant_name(restaurant);
//            feedback.setUser_name(user);
//
//            dataBase.saveFeedback(feedback);
//        }
        for (var feedback : feedbacks) {
            addReview(feedback);
        }
    }

    @Override
    public List<Feedback> getReviewsByRestaurantName(String restaurantName) throws Exception {
        return dataBase.getFeedbacks().filter(f -> f.getRestaurantName().equals(restaurantName)).toList();
    }

}
