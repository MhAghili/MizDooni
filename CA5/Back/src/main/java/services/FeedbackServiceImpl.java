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
import utils.DTO.FeedbackDTO;

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
    public void addReview(FeedbackDTO feedback) throws Exception{
        var user = dataBase.getUsers()
                .filter(u -> u.getUsername().equals(feedback.getUsername()))
                .findFirst()
                .orElseThrow(UserNotFound::new);

        var restaurant = dataBase.getRestaurants()
                .filter(r -> r.getName().equals(feedback.getRestaurantName()))
                .findFirst()
                .orElseThrow(RestaurantNotFound::new);


        if (user.getRole() == UserType.manager) {
            throw new RoleException();
        }



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
                    .filter(i -> i.getUser().getUsername().equals(feedback.getUsername()) && i.getRestaurant().getName().equals(feedback.getRestaurantName()))
                        .findFirst().orElse(null);
        if (previousFeedBack != null) {
            dataBase.deleteFeedback(previousFeedBack);
        }

        var newFeedback = new Feedback(
                feedback.getUsername(),
                feedback.getRestaurantName(),
                feedback.getServiceRate(),
                feedback.getFoodRate(),
                feedback.getAmbianceRate(),
                feedback.getOverallRate(),
                feedback.getComment(),
                user,
                restaurant
        );

        dataBase.saveFeedback(newFeedback);
    }

    @Override
    public void save(List<FeedbackDTO> feedbacks) throws Exception {
        for (var feedback : feedbacks) {
            addReview(feedback);
        }
    }

    @Override
    public List<Feedback> getReviewsByRestaurantName(String restaurantName) throws Exception {
        return dataBase.getFeedbacks().filter(f -> f.getRestaurant().getName().equals(restaurantName)).toList();
    }

}
