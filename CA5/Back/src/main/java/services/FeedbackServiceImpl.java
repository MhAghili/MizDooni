package services;

import defines.AllowedRateRange;
import enums.UserType;
import exceptions.*;
import interfaces.FeedbackService;
import models.Feedback;
import static defines.AllowedRateRange.*;
import models.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import utils.DTO.FeedbackDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static FeedbackService instance = null;

    private final SessionFactory sessionFactory;

    private FeedbackServiceImpl(){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FeedbackService getInstance() {
        if (instance == null)
            instance = new FeedbackServiceImpl();
        return instance;
    }

    @Override
    public void addReview(FeedbackDTO feedback) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", feedback.getUsername())
                    .uniqueResultOptional()
                    .orElseThrow(UserNotFound::new);

            Restaurant restaurant = session.createQuery("FROM Restaurant WHERE name = :restaurantName", Restaurant.class)
                    .setParameter("restaurantName", feedback.getRestaurantName())
                    .uniqueResultOptional()
                    .orElseThrow(RestaurantNotFound::new);

            if (user.getRole() == UserType.manager) {
                throw new RoleException();
            }

            if ((feedback.getServiceRate() < MIN_RATE || feedback.getServiceRate() > MAX_RATE) ||
                    (feedback.getFoodRate() < MIN_RATE || feedback.getFoodRate() > MAX_RATE) ||
                    (feedback.getAmbianceRate() < MIN_RATE || feedback.getAmbianceRate() > MAX_RATE) ||
                    (feedback.getOverallRate() < MIN_RATE || feedback.getOverallRate() > MAX_RATE)) {
                throw new AllowedRangeException();
            }

            Feedback previousFeedback = session.createQuery("FROM Feedback WHERE user.username = :username " +
                            "AND restaurant.name = :restaurantName", Feedback.class)
                    .setParameter("username", feedback.getUsername())
                    .setParameter("restaurantName", feedback.getRestaurantName())
                    .uniqueResultOptional()
                    .orElse(null);

            if (previousFeedback != null) {
                session.delete(previousFeedback);
            }

            Feedback newFeedback = new Feedback(
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

            session.save(newFeedback);
            session.getTransaction().commit();

        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void save(List<FeedbackDTO> feedbacks) throws Exception {
        for (var feedback : feedbacks) {
            addReview(feedback);
        }
    }

    @Override
    public List<Feedback> getReviewsByRestaurantName(String restaurantName) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Feedback WHERE restaurant.name = :restaurantName";
            return session.createQuery(hql, Feedback.class)
                    .setParameter("restaurantName", restaurantName)
                    .list();
        }
    }

}
