package interfaces;
import defines.AllowedRateRange;
import models.Feedback;

import java.util.List;

import static defines.AllowedRateRange.*;
public interface FeedbackService  {

    void addReview(Feedback feedback) throws Exception;

    List<Feedback> getReviewsByRestaurantName(String restaurantName) throws Exception;

}
