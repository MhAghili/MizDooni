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
import utils.DTO.FeedbackDTO;
import utils.DTO.RestaurantTableDTO;
import utils.ReservationCancellationRequest;
import utils.DTO.RestaurantDTO;
import utils.Utils;

import java.time.LocalDate;
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
            if(restaurantData.getStartTime().getMinutes() != 0 || restaurantData.getEndTime().getMinutes() != 0)
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

            // Check if the table number already exists for the given restaurant
            String hql = "SELECT COUNT(t) FROM RestaurantTable t WHERE t.restaurant.name = :restaurantName AND t.tableNumber = :tableNumber";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("restaurantName", table.getRestaurantName())
                    .setParameter("tableNumber", table.getTableNumber())
                    .uniqueResult();
            if (count > 0) {
                throw new TableNumberAlreadyTaken();
            }

            // Fetch the manager user
            hql = "SELECT u FROM User u WHERE u.username = :managerUsername";
            User managerUser = session.createQuery(hql, User.class)
                    .setParameter("managerUsername", table.getManagerUsername())
                    .uniqueResult();
            if (managerUser == null || managerUser.getRole() != UserType.manager) {
                throw new InvalidManagerUsername();
            }

            // Fetch the restaurant
            hql = "SELECT r FROM Restaurant r WHERE r.name = :restaurantName";
            Restaurant restaurant = session.createQuery(hql, Restaurant.class)
                    .setParameter("restaurantName", table.getRestaurantName())
                    .uniqueResult();
            if (restaurant == null) {
                throw new RestaurantNotFound();
            }

            // Create and save the new table
            var newTable = new RestaurantTable(table.getTableNumber(), table.getSeatsNumber(), managerUser, restaurant);
            session.save(newTable);

            // Commit the transaction
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e; // Rethrow the exception to ensure the caller knows about it
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
            throw e; // Rethrow the exception to ensure the caller knows about it
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
            String hql = "FROM Restaurant r WHERE r.name = :name";
            return session.createQuery(hql, Restaurant.class)
                    .setParameter("name", name)
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


//    @Override
//    public int reserveTable(TableReservationDTO reservation) throws Exception {
//        var availableTableInfos =
//                getAvailableTablesByRestaurant(reservation.getRestaurantName(), reservation.getDatetime())
//                        .stream()
//                        .filter(i -> i.getSeatsNumber() >= reservation.getNumberOfPeople().intValue())
//                        .sorted(Comparator.comparingInt(AvailableTableInfo::getSeatsNumber)
//                                .thenComparing(AvailableTableInfo::getSeatsNumber))
//                        .collect(Collectors.toList());
//
//        if (availableTableInfos.size() == 0)
//            throw new TableNotFoundException();
//
//
//        var user = dataBase.getUsers()
//                .filter(u -> u.getUsername().equals(reservation.getUsername()))
//                .findFirst()
//                .orElseThrow(UserNotFound::new);
//
//        if (user.getRole() == UserType.manager) {
//            throw new RoleException();
//        }
//
//        var restaurant = dataBase.getRestaurants()
//                .filter(r -> r.getName().equals(reservation.getRestaurantName()))
//                .findFirst()
//                .orElseThrow(RestaurantNotFound::new);
//
//        var tables = dataBase.getTables()
//                .filter(t -> t.getRestaurant().getName().equals(reservation.getRestaurantName()))
//                .toList();
//
//
//        if (!(reservation.getDatetime().getHours()>restaurant.getStartTime().getHours() && reservation.getDatetime().getHours()<restaurant.getEndTime().getHours())) {
//            throw new OutsideBusinessHoursException();
//        }
//
////        if (reservation.getDatetime().before(new Date())) {
////            throw new PastDateTimeException();
////        }
//
//        if (dataBase.getReservations().anyMatch(r ->
//                r.getRestaurant().getName().equals(reservation.getRestaurantName()) &&
//                        r.getTableNumber() == reservation.getTableNumber() &&
//                        r.getDatetime().equals(reservation.getDatetime()))) {
//            throw new TimeSlotAlreadyBookedException();
//        }
//
//        int reservationNumber = generateUniqueReservationNumber(); // Generate a unique reservation number
//        TableReservation newReservation = new TableReservation(reservationNumber, reservation.getUsername(),
//                reservation.getRestaurantName(), availableTableInfos.get(0).getTableNumber(), reservation.getDatetime(),user, restaurant);
//
//        dataBase.saveReservation(newReservation);
//        return newReservation.getNumber();
//    }


//    @Override
//    public List<Integer> getAvailableTimesByRestaurant(String restaurantName, Date date) throws Exception {
//        var availableTimes = new HashSet<Date>();
//        var availableTableInfos = getAvailableTablesByRestaurant(restaurantName, date);
//
//        for (var availableTableInfo : availableTableInfos) {
//            for (var availableTime : availableTableInfo.getAvailableTimes()) {
//                availableTimes.add(availableTime);
//            }
//        }
//
//        return availableTimes.stream().map(Date::getHours).sorted().collect(Collectors.toList());
//    }

//    @Override
//    public void cancelReservation(ReservationCancellationRequest request) throws Exception {
//        var reservation = dataBase.getReservations()
//                .filter(i -> i.getUser().getUsername().equals(request.getUsername()) && i.getNumber() == request.getReservationNumber())
//                .findFirst()
//                .orElse(null);
//
//        if (reservation == null)
//            throw new InvalidReservationNumber();
//
////        if (reservation.getDatetime().before(new Date()))
////            throw new CannotCancelReservationBecauseOfDate();
//
//        dataBase.deleteReservation(request.getUsername(), request.getReservationNumber());
//    }

    @Override
    public void cancelReservation(ReservationCancellationRequest request) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Find the reservation
            String hql = "FROM TableReservation tr WHERE tr.user.username = :username AND tr.number = :reservationNumber";
            TableReservation reservation = session.createQuery(hql, TableReservation.class)
                    .setParameter("username", request.getUsername())
                    .setParameter("reservationNumber", request.getReservationNumber())
                    .uniqueResult();

            if (reservation == null) {
                throw new InvalidReservationNumber();
            }

            // Delete the reservation
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
            throw e; // Rethrow the exception to ensure the caller knows about it
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


//    @Override
//    public List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName, Date date) throws Exception{
//        if (!dataBase.getRestaurants().anyMatch(i -> i.getName().equals(restaurantName)))
//            throw new InvalidRestaurantName();
//
//        var instant = date.toInstant();
//
//        // Convert Instant to ZonedDateTime with default time zone
//        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
//
//        // Extract LocalDate from ZonedDateTime
//        LocalDate currentDate = zonedDateTime.toLocalDate();
//
//        var restaurant = dataBase.getRestaurants().filter(i -> i.getName().equals(restaurantName)).findFirst().orElse(null);
//        var tables = dataBase.getTables().filter(i -> i.getRestaurant().getName().equals(restaurantName)).toList();
//
//
//        var result = new ArrayList<AvailableTableInfo>();
//        for (var table: tables) {
//            Supplier<Stream<TableReservation>> tableReservations = () -> dataBase.getReservations().filter(i -> i.getRestaurant().getName().equals(restaurantName) && i.getTableNumber() == table.getTableNumber() && isTwoDateEqual(i.getDatetime(), currentDate));
//
//            var availableTimes = new ArrayList<Date>();
//            for (int i = 0; i <= 23 ; i++) {
//                int finalI = i;
//                if(i <= restaurant.getEndTime().getHours()
//                        && i >= restaurant.getStartTime().getHours()
//                        && !tableReservations.get().anyMatch(j -> j.getDatetime().getHours() == finalI)
//                    ) {
//                    Calendar calendar = Calendar.getInstance();
//
//                    calendar.set(Calendar.YEAR, currentDate.getYear());
//                    calendar.set(Calendar.MONTH, currentDate.getMonthValue() - 1);
//                    calendar.set(Calendar.DAY_OF_MONTH, currentDate.getDayOfMonth());
//                    calendar.set(Calendar.HOUR_OF_DAY, i);
//                    calendar.set(Calendar.MINUTE, 0);
//                    calendar.set(Calendar.SECOND, 0);
//                    calendar.set(Calendar.MILLISECOND, 0);
//
//                    availableTimes.add(calendar.getTime());
//                }
//            }
//            result.add(new AvailableTableInfo(table.getTableNumber(), table.getSeatsNumber(), availableTimes));
//        }
//        dataBase.getReservations().filter(i -> i.getRestaurant().getName().equals(restaurantName));
//        return result;
//    }


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

    private boolean isTwoDateEqual(Date date1, LocalDate date2) {
        return date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonthValue()
                && date1.getDay() == date2.getDayOfMonth();
    }
    private boolean addressIsInInvalidFormat(RestaurantAddress address) {
        return Utils.isNullOrEmptyString(address.getCity())
                || Utils.isNullOrEmptyString(address.getCountry())
                || Utils.isNullOrEmptyString((address.getStreet()));
    }
//    private int generateUniqueReservationNumber() {
//        return dataBase.getReservationCounter();
//    }


}
