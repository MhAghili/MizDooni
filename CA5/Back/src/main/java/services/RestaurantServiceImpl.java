package services;

import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.RestaurantService;
import models.*;
import org.springframework.stereotype.Service;
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

import DataBase.*;
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static RestaurantService instance = null;
    private DataBase dataBase;

    private RestaurantServiceImpl() {
        this.dataBase = MemoryDataBase.getInstance();
    }

    public static RestaurantService getInstance() {
        if (instance == null)
            instance = new RestaurantServiceImpl();
        return instance;
    }
    @Override
    public void addRestaurant(RestaurantDTO restaurantData) throws Exception {
        if (dataBase.getRestaurants().anyMatch(i-> i.getName().equals(restaurantData.getName())))
            throw new RestaurantNameAlreadyTaken();

        var managerUser = dataBase.getUsers().filter(i -> i.getUsername().equals(restaurantData.getManagerUsername())).findFirst().orElse(null);
        if(managerUser == null || managerUser.getRole() != UserType.manager)
            throw new InvalidManagerUsername();

        if(restaurantData.getStartTime().getMinutes() != 0 || restaurantData.getEndTime().getMinutes() != 0)
            throw new TimeOfRestaurantShouldBeRound();

        if(addressIsInInvalidFormat(restaurantData.getAddress()))
            throw new AddressShouldContainsCityAndCountryAndStreet();


        var restaurant = new Restaurant(restaurantData.getName(), restaurantData.getManagerUsername(),managerUser, restaurantData.getType(), restaurantData.getStartTime(), restaurantData.getEndTime(), restaurantData.getDescription(),restaurantData.getAddress(),restaurantData.getImage()  );


        dataBase.saveRestaurant(restaurant);

    }
    
    @Override
    public void save(List<RestaurantDTO> restaurants) throws Exception {
        for (var restaurant : restaurants) {
//            var managerUser = dataBase.getUsers().filter(i -> i.getUsername().equals(restaurant.getManagerUsername())).findFirst().orElse(null);
//            restaurant.setManager(managerUser);
//            dataBase.saveRestaurant(restaurant);
            addRestaurant(restaurant);
        }
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        var existingRestaurant = dataBase.getRestaurants().filter(i -> i.getName().equals(restaurant.getName())).findFirst();

        if(existingRestaurant.isEmpty())
            dataBase.saveRestaurant(restaurant);
        else {
            dataBase.deleteRestaurant(existingRestaurant.get());
            dataBase.saveRestaurant(restaurant);
        }
    }

    @Override
    public void saveTables(List<RestaurantTable> tables) throws Exception {
        for (var table : tables) {
            addTable(table);
        }
    }

    @Override
    public void addTable(RestaurantTable table) throws Exception {
        if (dataBase.getTables().anyMatch(a -> a.getRestaurantName().equals(table.getRestaurantName()) && a.getTableNumber() == table.getTableNumber()))
            throw new TableNumberAlreadyTaken();

        var managerUser = dataBase.getUsers().filter(i -> i.getUsername().equals(table.getManagerUsername())).findFirst().orElse(null);
        var restaurant = dataBase.getRestaurants().filter(i -> i.getName().equals(table.getRestaurantName())).findFirst().orElse(null);
        if(managerUser == null || managerUser.getRole() != UserType.manager)
            throw new InvalidManagerUsername();

        if(!(dataBase.getRestaurants().anyMatch(a -> a.getName().equals(table.getRestaurantName()))))
            throw new RestaurantNotFound();

        table.setUser( managerUser);
        table.setRestaurant(restaurant);


        dataBase.saveTable(table);

    }

    @Override
    public List<RestaurantTable> getTablesByRestaurant(String restaurantName) throws Exception {
        return dataBase.getTables().filter(i -> i.getRestaurantName().equals(restaurantName)).toList();
    }
    @Override
    public List<Restaurant> getRestaurantsByName(String name) {
        return dataBase.getRestaurants().filter(i -> i.getName().equals(name)).toList();
    }
    @Override
    public List<TableReservation> getReservationsByRestaurant(String restaurantName) throws Exception {
        return dataBase.getReservations().filter(i -> i.getRestaurantName().equals(restaurantName)).toList();
    }

    @Override
    public int reserveTable(TableReservation reservation) throws Exception {
        var availableTableInfos =
                getAvailableTablesByRestaurant(reservation.getRestaurantName(), reservation.getDatetime())
                        .stream()
                        .filter(i -> i.getSeatsNumber() >= reservation.getNumberOfPeople().intValue())
                        .sorted(Comparator.comparingInt(AvailableTableInfo::getSeatsNumber)
                                .thenComparing(AvailableTableInfo::getSeatsNumber))
                        .collect(Collectors.toList());

        if (availableTableInfos.size() == 0)
            throw new TableNotFoundException();


        var user = dataBase.getUsers()
                .filter(u -> u.getUsername().equals(reservation.getUsername()))
                .findFirst()
                .orElseThrow(UserNotFound::new);

        if (user.getRole() == UserType.manager) {
            throw new RoleException();
        }

        var restaurant = dataBase.getRestaurants()
                .filter(r -> r.getName().equals(reservation.getRestaurantName()))
                .findFirst()
                .orElseThrow(RestaurantNotFound::new);

        var tables = dataBase.getTables()
                .filter(t -> t.getRestaurantName().equals(reservation.getRestaurantName()))
                .toList();


        if (!(reservation.getDatetime().getHours()>restaurant.getStartTime().getHours() && reservation.getDatetime().getHours()<restaurant.getEndTime().getHours())) {
            throw new OutsideBusinessHoursException();
        }

//        if (reservation.getDatetime().before(new Date())) {
//            throw new PastDateTimeException();
//        }

        if (dataBase.getReservations().anyMatch(r ->
                r.getRestaurantName().equals(reservation.getRestaurantName()) &&
                        r.getTableNumber() == reservation.getTableNumber() &&
                        r.getDatetime().equals(reservation.getDatetime()))) {
            throw new TimeSlotAlreadyBookedException();
        }

        int reservationNumber = generateUniqueReservationNumber(); // Generate a unique reservation number
        TableReservation newReservation = new TableReservation(reservationNumber, reservation.getUsername(),
                reservation.getRestaurantName(), availableTableInfos.get(0).getTableNumber(), reservation.getDatetime(),user, restaurant);

        dataBase.saveReservation(newReservation);
        return newReservation.getNumber();
    }


    @Override
    public List<Integer> getAvailableTimesByRestaurant(String restaurantName, Date date) throws Exception {
        var availableTimes = new HashSet<Date>();
        var availableTableInfos = getAvailableTablesByRestaurant(restaurantName, date);

        for (var availableTableInfo : availableTableInfos) {
            for (var availableTime : availableTableInfo.getAvailableTimes()) {
                availableTimes.add(availableTime);
            }
        }

        return availableTimes.stream().map(Date::getHours).sorted().collect(Collectors.toList());
    }

    @Override
    public void cancelReservation(ReservationCancellationRequest request) throws Exception {
        var reservation = dataBase.getReservations()
                .filter(i -> i.getUsername().equals(request.getUsername()) && i.getNumber() == request.getReservationNumber())
                .findFirst()
                .orElse(null);

        if (reservation == null)
            throw new InvalidReservationNumber();

//        if (reservation.getDatetime().before(new Date()))
//            throw new CannotCancelReservationBecauseOfDate();

        dataBase.deleteReservation(request.getUsername(), request.getReservationNumber());
    }

    @Override
    public Restaurant getRestaurantByName(String name) throws Exception {
        var result = dataBase.getRestaurants()
                        .filter(i -> i.getName().equals(name))
                        .findFirst()
                        .orElse(null);

//        if (result == null)
//            throw new InvalidRestaurantName();

        return result;
    }
    @Override
    public List<Restaurant> getRestaurantByType(String type) {
        return dataBase.getRestaurants().filter(i -> i.getType().equals(type)).toList();
    }

    @Override
    public List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName, Date date) throws Exception{
        if (!dataBase.getRestaurants().anyMatch(i -> i.getName().equals(restaurantName)))
            throw new InvalidRestaurantName();

        var instant = date.toInstant();

        // Convert Instant to ZonedDateTime with default time zone
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        // Extract LocalDate from ZonedDateTime
        LocalDate currentDate = zonedDateTime.toLocalDate();

        var restaurant = dataBase.getRestaurants().filter(i -> i.getName().equals(restaurantName)).findFirst().orElse(null);
        var tables = dataBase.getTables().filter(i -> i.getRestaurantName().equals(restaurantName)).toList();


        var result = new ArrayList<AvailableTableInfo>();
        for (var table: tables) {
            Supplier<Stream<TableReservation>> tableReservations = () -> dataBase.getReservations().filter(i -> i.getRestaurantName().equals(restaurantName) && i.getTableNumber() == table.getTableNumber() && isTwoDateEqual(i.getDatetime(), currentDate));

            var availableTimes = new ArrayList<Date>();
            for (int i = 0; i <= 23 ; i++) {
                int finalI = i;
                if(i <= restaurant.getEndTime().getHours()
                        && i >= restaurant.getStartTime().getHours()
                        && !tableReservations.get().anyMatch(j -> j.getDatetime().getHours() == finalI)
                    ) {
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.YEAR, currentDate.getYear());
                    calendar.set(Calendar.MONTH, currentDate.getMonthValue() - 1);
                    calendar.set(Calendar.DAY_OF_MONTH, currentDate.getDayOfMonth());
                    calendar.set(Calendar.HOUR_OF_DAY, i);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    availableTimes.add(calendar.getTime());
                }
            }
            result.add(new AvailableTableInfo(table.getTableNumber(), table.getSeatsNumber(), availableTimes));
        }
        dataBase.getReservations().filter(i -> i.getRestaurantName().equals(restaurantName));
        return result;
    }

    @Override
    public Restaurant getCurrentRes(String restaurantName) throws Exception {
        return dataBase.getRestaurants().filter(i -> i.getName().equals(restaurantName)).findFirst().orElse(null);
    }

    @Override
    public List<TableReservation> getReservationByUsername(String username) {
        return dataBase.getReservations()
                .filter(i -> i.getUsername().equals(username))
                .toList();
    }

    @Override
    public List<Restaurant> getRestaurantsByManager(String managerUsername) throws Exception {
        return dataBase.getRestaurants().filter(i -> i.getManagerUsername().equals(managerUsername)).toList();
    }

    @Override
    public Feedback getAverageFeedbackOfRestaurant(String restaurnatName) {
        var feedbacks = dataBase.getFeedbacks().filter(i -> i.getRestaurantName().equals(restaurnatName));
        long count = feedbacks.count(); // Count the number of feedbacks

        if (count == 0) {
            return new Feedback("", restaurnatName, 0, 0, 0, 0, "");
        }

        return new Feedback( "", restaurnatName,
                dataBase.getFeedbacks().filter(i -> i.getRestaurantName().equals(restaurnatName)).map(i -> i.getFoodRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
                dataBase.getFeedbacks().filter(i -> i.getRestaurantName().equals(restaurnatName)).map(i -> i.getServiceRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
                dataBase.getFeedbacks().filter(i -> i.getRestaurantName().equals(restaurnatName)).map(i -> i.getAmbianceRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
                dataBase.getFeedbacks().filter(i -> i.getRestaurantName().equals(restaurnatName)).map(i -> i.getOverallRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
            "");
    }

    @Override
    public List<TableReservation> getReservationsByUserName(String userName ) throws Exception {
        return dataBase.getReservations().filter(i -> i.getUsername().equals(userName)).toList();
    }

    @Override
    public List<RestaurantTable> getTablesByRestaurantName(String restaurantName) throws Exception {
        return dataBase.getTables().filter(i -> i.getRestaurantName().equals(restaurantName)).toList();
    }

    @Override
    public List<Restaurant> getRestaurantsByCity(String cityName) {
        return dataBase.getRestaurants().filter(i -> i.getAddress().getCity().equals(cityName)).toList();
    }

    @Override
    public List<Restaurant> fetchAll() { return dataBase.getRestaurants().toList(); }

    @Override
    public void delete(String restaurantName) throws Exception {
        var restaurant =
            dataBase.getRestaurants().filter(i -> i.getName().equals(restaurantName)).findFirst();

        if (restaurant.isEmpty())
            throw new InvalidRestaurantName();

        dataBase.deleteRestaurant(restaurant.get());
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
    private int generateUniqueReservationNumber() {
        return dataBase.getReservationCounter();
    }


}
