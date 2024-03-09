package services;

import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.RestaurantService;
import models.*;
import utils.ReservationCancellationRequest;
import utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import DataBase.*;

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
    public void addRestaurant(Restaurant restaurant) throws Exception {
        if (dataBase.getRestaurants().anyMatch(i-> i.getName().equals(restaurant.getName())))
            throw new RestaurantNameAlreadyTaken();

        var managerUser = dataBase.getUsers().filter(i -> i.getUsername().equals(restaurant.getManagerUsername())).findFirst().orElse(null);
        if(managerUser == null || managerUser.getRole() != UserType.manager)
            throw new InvalidManagerUsername();

        if(restaurant.getStartTime().getMinutes() != 0 || restaurant.getEndTime().getMinutes() != 0)
            throw new TimeOfRestaurantShouldBeRound();

        if(addressIsInInvalidFormat(restaurant.getAddress()))
            throw new AddressShouldContainsCityAndCountryAndStreet();

        dataBase.saveRestaurant(restaurant);

    }

    @Override
    public void addTable(Table table) throws Exception {
        if (dataBase.getTables().anyMatch(a -> a.getRestaurantName() == table.getRestaurantName() && a.getTableNumber() == table.getTableNumber()))
            throw new TableNumberAlreadyTaken();

        var managerUser = dataBase.getUsers().filter(i -> i.getUsername().equals(table.getManagerUsername())).findFirst().orElse(null);
        if(managerUser == null || managerUser.getRole() != UserType.manager)
            throw new InvalidManagerUsername();

        if(!(dataBase.getRestaurants().anyMatch(a -> a.getName().equals(table.getRestaurantName()))))
            throw new RestaurantNotFound();

        dataBase.saveTable(table);

    }

    @Override
    public List<Table> getTablesByRestaurant(String restaurantName) throws Exception {
        return dataBase.getTables().filter(i -> i.getRestaurantName().equals(restaurantName)).toList();
    }

    @Override
    public int reserveTable(TableReservation reservation) throws Exception {

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

        if (!tables.stream().anyMatch(i -> i.getTableNumber() == reservation.getTableNumber())) {
            throw new TableNotFoundException();
        }

        if (!(reservation.getDatetime().getHours()>restaurant.getStartTime().getHours() && reservation.getDatetime().getHours()<restaurant.getEndTime().getHours())) {
            throw new OutsideBusinessHoursException();
        }

        if (reservation.getDatetime().before(new Date())) {
            throw new PastDateTimeException();
        }

        if (dataBase.getReservations().anyMatch(r ->
                r.getRestaurantName().equals(reservation.getRestaurantName()) &&
                        r.getTableNumber() == reservation.getTableNumber() &&
                        r.getDatetime().equals(reservation.getDatetime()))) {
            throw new TimeSlotAlreadyBookedException();
        }

        int reservationNumber = generateUniqueReservationNumber(); // Generate a unique reservation number
        TableReservation newReservation = new TableReservation(reservationNumber, reservation.getUsername(),
                reservation.getRestaurantName(), reservation.getTableNumber(), reservation.getDatetime());

        dataBase.saveReservation(newReservation);
        return newReservation.getNumber();
    }


    @Override
    public void cancelReservation(ReservationCancellationRequest request) throws Exception {
        var reservation = dataBase.getReservations()
                            .filter(i -> i.getUsername().equals(request.getUsername()) && i.getNumber() == request.getReservationNumber())
                            .findFirst()
                            .orElse(null);

        if (reservation == null)
            throw new InvalidReservationNumber();

        if (reservation.getDatetime().before(new Date()))
            throw new CannotCancelReservationBecauseOfDate();

        dataBase.deleteReservation(request.getUsername(), request.getReservationNumber());
    }

    @Override
    public Restaurant getRestaurantByName(String name) throws Exception {
        var result = dataBase.getRestaurants()
                        .filter(i -> i.getName().contains(name))
                        .findFirst()
                        .orElse(null);

        if (result == null)
            throw new InvalidRestaurantName();

        return result;
    }
    @Override
    public List<Restaurant> getRestaurantByType(String type) {
        return dataBase.getRestaurants().filter(i -> i.getType().equals(type)).toList();
    }

    @Override
    public List<AvailableTableInfo> getAvailableTablesByRestaurant(String restaurantName) throws Exception{
        if (!dataBase.getRestaurants().anyMatch(i -> i.getName().equals(restaurantName)))
            throw new InvalidRestaurantName();

        var currentDate = new Date();
        var restaurant = dataBase.getRestaurants().filter(i -> i.getName().equals(restaurantName)).findFirst().orElse(null);
        var tables = dataBase.getTables().filter(i -> i.getRestaurantName().equals(restaurantName)).toList();


        var result = new ArrayList<AvailableTableInfo>();
        for (var table: tables) {
            var tableReservations = dataBase.getReservations().filter(i -> i.getRestaurantName().equals(restaurantName) && i.getTableNumber() == table.getTableNumber());

            var availableTimes = new ArrayList<Date>();
            for (int i = 1; i <= 24 ; i++) {
                if(currentDate.getHours() + i <= restaurant.getEndTime().getHours()
                        && currentDate.getHours() + i >= restaurant.getStartTime().getHours()
                        && currentDate.getHours() + i <= 24
                        && !tableReservations.anyMatch(j -> isTwoDateEqual(j.getDatetime(), currentDate))
                    ) {
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.YEAR, currentDate.getYear());
                    calendar.set(Calendar.MONTH, currentDate.getMonth());
                    calendar.set(Calendar.DAY_OF_MONTH, currentDate.getDay());
                    calendar.set(Calendar.HOUR_OF_DAY, currentDate.getHours() + i);
                    calendar.set(Calendar.MINUTE, 0);

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
    public Restaurant getRestaurantByManager(String managerUsername) throws Exception {
        return dataBase.getRestaurants()
                .filter(i -> i.getManagerUsername().equals(managerUsername))
                .findFirst()
                .orElseThrow(RestaurantNotFound::new);
    }

    @Override
    public Feedback getAverageFeedbackOfRestaurant(String restaurnatName) {
        var feedbacks = dataBase.getFeedbacks().filter(i -> i.getRestaurantName().equals(restaurnatName));
        if (feedbacks.findAny().isEmpty())
            return new Feedback("",restaurnatName, 0,0,0,0, "");

        return new Feedback( "", restaurnatName,
            feedbacks.map(i -> i.getFoodRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
            feedbacks.map(i -> i.getServiceRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
            feedbacks.map(i -> i.getAmbianceRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
            feedbacks.map(i -> i.getOverallRate()).mapToDouble(Double::doubleValue).average().orElse(0.0),
            "");
    }

    private boolean isTwoDateEqual(Date date1, Date date2) {
        return date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDay() == date2.getDay()
                && date1.getHours() == date2.getHours()
                && date1.getMinutes() == date2.getMinutes();
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
