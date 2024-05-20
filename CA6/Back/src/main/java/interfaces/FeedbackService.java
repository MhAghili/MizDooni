package interfaces;
import defines.AllowedRateRange;
import models.Feedback;
import utils.DTO.FeedbackDTO;

import java.util.List;

import static defines.AllowedRateRange.*;
public interface FeedbackService  {

    void addReview(FeedbackDTO feedback) throws Exception;
    void save(List<FeedbackDTO> feedbacks) throws Exception;

    List<Feedback> getReviewsByRestaurantName(String restaurantName) throws Exception;

}
