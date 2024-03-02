package interfaces;
import defines.AllowedRateRange;
import models.Feedback;
import static defines.AllowedRateRange.*;
public interface FeedbackService  {

    void addReview(Feedback feedback) throws Exception;
}
