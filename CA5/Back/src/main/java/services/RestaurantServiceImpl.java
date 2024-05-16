package services;

import enums.UserType;
import exceptions.*;
import interfaces.RestaurantService;
import models.*;

import models.TableReservationDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import utils.AvailableTableInfo;
import utils.DTO.FeedbackDTO;
import utils.DTO.RestaurantTableDTO;
import utils.ReservationCancellationRequest;
import utils.DTO.RestaurantDTO;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static RestaurantService instance = null;

    private final SessionFactory sessionFactory;

    private RestaurantServiceImpl() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static RestaurantService getInstance() {
        if (instance == null)
            instance = new RestaurantServiceImpl();
        return instance;
    }
    @Override
    public void addRestaurant(RestaurantDTO restaurantData) throws Exception {

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String hql = "SELECT COUNT(r) FROM Restaurant r WHERE r.name = :restaurantName";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("restaurantName", restaurantData.getName())
                    .uniqueResult();
            if (count > 0) {
                throw new RestaurantNameAlreadyTaken();
            }

            hql = "SELECT u FROM User u WHERE u.username = :managerUsername";
            User manager = session.createQuery(hql, User.class)
                    .setParameter("managerUsername", restaurantData.getManagerUsername())
                    .uniqueResult();
            if (manager == null || manager.getRole() != UserType.manager) {
                throw new InvalidManagerUsername();
            }
            if(restaurantData.getStartTime().getMinute() != 0 || restaurantData.getEndTime().getMinute() != 0)
                throw new TimeOfRestaurantShouldBeRound();

            if(addressIsInInvalidFormat(restaurantData.getAddress()))
                throw new AddressShouldContainsCityAndCountryAndStreet();

            var restaurant = new Restaurant(restaurantData.getName(),manager, restaurantData.getType(), restaurantData.getStartTime(), restaurantData.getEndTime(), restaurantData.getDescription(),restaurantData.getAddress(),restaurantData.getImage()  );

            session.save(restaurant);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public void save(List<RestaurantDTO> restaurants) throws Exception {
        for (var restaurant : restaurants) {
            addRestaurant(restaurant);
        }
    }


    @Override
    public void saveTables(List<RestaurantTableDTO> tables) throws Exception {
        for (var table : tables) {
            addTable(table);
        }
    }


    @Override
    public void addTable(RestaurantTableDTO table) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();


            String hql = "SELECT COUNT(t) FROM RestaurantTable t WHERE t.restaurant.name = :restaurantName AND t.tableNumber = :tableNumber";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("restaurantName", table.getRestaurantName())
                    .setParameter("tableNumber", table.getTableNumber())
                    .uniqueResult();
            if (count > 0) {
                throw new TableNumberAlreadyTaken();
            }


            hql = "SELECT u FROM User u WHERE u.username = :managerUsername";
            User managerUser = session.createQuery(hql, User.class)
                    .setParameter("managerUsername", table.getManagerUsername())
                    .uniqueResult();
            if (managerUser == null || managerUser.getRole() != UserType.manager) {
                throw new InvalidManagerUsername();
            }


            hql = "SELECT r FROM Restaurant r WHERE r.name = :restaurantName";
            Restaurant restaurant = session.createQuery(hql, Restaurant.class)
                    .setParameter("restaurantName", table.getRestaurantName())
                    .uniqueResult();
            if (restaurant == null) {
                throw new RestaurantNotFound();
            }


            var newTable = new RestaurantTable(table.getTableNumber(), table.getSeatsNumber(), managerUser, restaurant);
            session.save(newTable);


            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public List<RestaurantTable> getTablesByRestaurant(String restaurantName) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM RestaurantTable t WHERE t.restaurant.name = :restaurantName";
            return session.createQuery(hql, RestaurantTable.class)
                    .setParameter("restaurantName", restaurantName)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public List<Restaurant> getRestaurantsByName(String name) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Restaurant r WHERE r.name LIKE :name";
            return session.createQuery(hql, Restaurant.class)
                    .setParameter("name", "%" + name + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public List<TableReservation> getReservationsByRestaurant(String restaurantName) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM TableReservation tr WHERE tr.restaurant.name = :restaurantName";
            return session.createQuery(hql, TableReservation.class)
                    .setParameter("restaurantName", restaurantName)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Rethrow the exception to ensure the caller knows about it
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void cancelReservation(ReservationCancellationRequest request) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();


            String hql = "FROM TableReservation tr WHERE tr.user.username = :username AND tr.reservationNumber = :reservationNumber";
            TableReservation reservation = session.createQuery(hql, TableReservation.class)
                    .setParameter("username", request.getUsername())
                    .setParameter("reservationNumber", request.getReservationNumber())
                    .uniqueResult();

            if (reservation == null) {
                throw new InvalidReservationNumber();
            }


            session.delete(reservation);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public Restaurant getRestaurantByName(String name) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Restaurant r WHERE r.name = :name";
            Restaurant restaurant = session.createQuery(hql, Restaurant.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return restaurant;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
    @Override
    public List<Restaurant> getRestaurantByType(String type) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Restaurant WHERE type = :type";
            return session.createQuery(hql, Restaurant.class)
                    .setParameter("type", type)
                    .list();
        }
    }

    @Override
    public List<TableReservation> getReservationByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM TableReservation WHERE user.username = :username";
            return session.createQuery(hql, TableReservation.class)
                    .setParameter("username", username)
                    .list();
        }
    }


    @Override
    public List<Restaurant> getRestaurantsByManager(String managerUsername) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT r FROM Restaurant r JOIN FETCH r.manager WHERE r.manager.username = :managerUsername";
            return session.createQuery(hql, Restaurant.class)
                    .setParameter("managerUsername", managerUsername)
                    .list();
        }
    }

    @Override
    public FeedbackDTO getAverageFeedbackOfRestaurant(String restaurantName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT " +
                    "AVG(f.foodRate), AVG(f.serviceRate), AVG(f.ambianceRate), AVG(f.overallRate) " +
                    "FROM Feedback f " +
                    "WHERE f.restaurant.name = :restaurantName";
            Object[] result = (Object[]) session.createQuery(hql)
                    .setParameter("restaurantName", restaurantName)
                    .uniqueResult();

            double foodRateAvg = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
            double serviceRateAvg = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
            double ambianceRateAvg = result[2] != null ? ((Number) result[2]).doubleValue() : 0.0;
            double overallRateAvg = result[3] != null ? ((Number) result[3]).doubleValue() : 0.0;

            return new FeedbackDTO("", restaurantName, foodRateAvg, serviceRateAvg, ambianceRateAvg, overallRateAvg, "");
        }
    }


    @Override
    public List<TableReservation> getReservationsByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM TableReservation WHERE user.username = :username";
            return session.createQuery(hql, TableReservation.class)
                    .setParameter("username", userName)
                    .list();
        }
    }


    @Override
    public List<RestaurantTable> getTablesByRestaurantName(String restaurantName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM RestaurantTable WHERE restaurant.name = :restaurantName";
            return session.createQuery(hql, RestaurantTable.class)
                    .setParameter("restaurantName", restaurantName)
                    .list();
        }
    }


    @Override
    public List<Restaurant> fetchAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Restaurant", Restaurant.class).list();
        }
    }

    @Override
    public void delete(String restaurantName) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Restaurant restaurant = session.createQuery("FROM Restaurant WHERE name = :name", Restaurant.class)
                    .setParameter("name", restaurantName)
                    .uniqueResult();

            if (restaurant == null) {
                throw new InvalidRestaurantName();
            }

            session.delete(restaurant);
            transaction.commit();
        }
    }

    @Override
    public List<AvailableTableInfo> showAvailableTimes(String restaurantName, LocalDate date) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            // Check if restaurant exists
            String restaurantHql = "FROM Restaurant r WHERE r.name = :restaurantName";
            Restaurant restaurant = session.createQuery(restaurantHql, Restaurant.class)
                    .setParameter("restaurantName", restaurantName)
                    .uniqueResult();

            if (restaurant == null) {
                throw new RestaurantNotFound();
            }


            String tableHql = "FROM RestaurantTable t WHERE t.restaurant.name = :restaurantName";
            List<RestaurantTable> tables = session.createQuery(tableHql, RestaurantTable.class)
                    .setParameter("restaurantName", restaurantName)
                    .list();

            String reservationHql = "FROM TableReservation tr WHERE tr.restaurant.name = :restaurantName AND DATE(tr.datetime) = :date";
            List<TableReservation> reservations = session.createQuery(reservationHql, TableReservation.class)
                    .setParameter("restaurantName", restaurantName)
                    .setParameter("date", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .list();


            List<AvailableTableInfo> availableTableInfos = new ArrayList<>();
            for (RestaurantTable table : tables) {
                List<ZonedDateTime> availableTimes = new ArrayList<>();
                for (int hour = restaurant.getStartTime().getHour(); hour < restaurant.getEndTime().getHour(); hour++) {
                    ZonedDateTime dateTime = date.atTime(hour, 0).atZone(ZoneId.systemDefault());
                    boolean isAvailable = reservations.stream()
                            .noneMatch(reservation -> reservation.getTableNumber()==table.getTableNumber()
                                    && reservation.getDatetime().toInstant().atZone(ZoneId.systemDefault()).equals(dateTime));
                    if (isAvailable) {
                        availableTimes.add(dateTime);
                    }
                }
                availableTableInfos.add(new AvailableTableInfo(table.getTableNumber(), table.getSeatsNumber(), availableTimes));
            }

            return availableTableInfos;
        }
    }

    @Override
    public void reserveTable(TableReservationDTO reservation) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();


            String userHql = "FROM User u WHERE u.username = :username";
            User user = session.createQuery(userHql, User.class)
                    .setParameter("username", reservation.getUsername())
                    .uniqueResult();

            if (user == null) {
                throw new UserNotFound();
            }

            if (user.getRole() == UserType.manager) {
                throw new RoleException();
            }

            String restaurantHql = "FROM Restaurant r WHERE r.name = :restaurantName";
            Restaurant restaurant = session.createQuery(restaurantHql, Restaurant.class)
                    .setParameter("restaurantName", reservation.getRestaurantName())
                    .uniqueResult();

            if (restaurant == null) {
                throw new RestaurantNotFound();
            }

            LocalDateTime dateTime = reservation.getDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (dateTime.getHour() < restaurant.getStartTime().getHour() || dateTime.getHour() >= restaurant.getEndTime().getHour()) {
                throw new OutsideBusinessHoursException();
            }

            String tableHql = "FROM RestaurantTable t WHERE t.restaurant.name = :restaurantName AND t.tableNumber = :tableNumber";
            RestaurantTable table = session.createQuery(tableHql, RestaurantTable.class)
                    .setParameter("restaurantName", reservation.getRestaurantName())
                    .setParameter("tableNumber", reservation.getTableNumber())
                    .uniqueResult();

            if (table == null) {
                throw new TableNotFoundException();
            }
            String reservationHql = "FROM TableReservation tr WHERE tr.restaurant.name = :restaurantName AND tr.tableNumber = :tableNumber AND tr.datetime = :datetime";
            TableReservation existingReservation = session.createQuery(reservationHql, TableReservation.class)
                    .setParameter("restaurantName", reservation.getRestaurantName())
                    .setParameter("tableNumber", reservation.getTableNumber())
                    .setParameter("datetime", reservation.getDatetime())
                    .uniqueResult();

            if (existingReservation != null) {
                throw new TimeSlotAlreadyBookedException();
            }

            TableReservation newReservation = new TableReservation( reservation.getTableNumber(), reservation.getDatetime(), user, restaurant, reservation.getNumberOfPeople());
            session.save(newReservation);

            transaction.commit();
        }
    }

    private boolean addressIsInInvalidFormat(RestaurantAddress address) {
        return Utils.isNullOrEmptyString(address.getCity())
                || Utils.isNullOrEmptyString(address.getCountry())
                || Utils.isNullOrEmptyString((address.getStreet()));
    }

}
