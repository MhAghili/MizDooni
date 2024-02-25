package services;

import enums.UserType;
import exceptions.*;
import interfaces.DataBase;
import interfaces.RestaurantService;
import models.Address;
import models.Restaurant;
import models.Table;
import models.TableReservation;
import utils.Utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {

    private DataBase dataBase;
    public RestaurantServiceImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }
    @Override
    public void addRestaurant(Restaurant restaurant) throws Exception {
        if (dataBase.getRestaurants().anyMatch(i-> i.getName() == restaurant.getName()))
            throw new RestaurantNameAlreadyTaken();

        var managerUser = dataBase.getUsers().filter(i -> i.getUsername() == restaurant.getManagerUsername()).findFirst().orElse(null);
        if(managerUser == null || managerUser.getUserType() != UserType.MANAGER)
            throw new InvalidManagerUsername();

        if(restaurant.getStartTime().getMinutes() != 0 || restaurant.getEndTime().getMinutes() != 0)
            throw new TimeOfRestaurantShouldBeRound();

        if(addressIsInInvalidFormat(restaurant.getAddress()))
            throw new AddressShouldContainsCityAndCountryAndStreet();

        dataBase.saveRestaurant(restaurant);
    }

    @Override
    public void addTable(Table table) throws Exception {
        if (dataBase.getTables().anyMatch(a -> a.getRestaurantName() == table.getRestaurantName() && a.getNumber() == table.getNumber()))
            throw new TableNumberAlreadyTaken();

        var managerUser = dataBase.getUsers().filter(i -> i.getUsername() == table.getManagerUsername()).findFirst().orElse(null);
        if(managerUser == null || managerUser.getUserType() != UserType.MANAGER)
            throw new InvalidManagerUsername();

        if(!dataBase.getRestaurants().anyMatch(a -> a.getName() == table.getRestaurantName()))
            throw new InvalidRestaurantName();

        dataBase.saveTable(table);
    }

    @Override
    public void cancelReservation(String username, int reservationNumber) throws Exception {
        var reservation = dataBase.getReservations()
                            .filter(i -> i.getUsername().equals(username) && i.getNumber() == reservationNumber)
                            .findFirst()
                            .orElse(null);

        if (reservation == null)
            throw new InvalidReservationNumber();

        if (reservation.getDateTime().before(new Date()))
            throw new CannotCancelReservationBecauseOfDate();

        dataBase.deleteReservation(username, reservationNumber);
    }

    @Override
    public Restaurant getRestaurantByName(String name) throws Exception {
        var result = dataBase.getRestaurants()
                        .filter(i -> i.getName().equals(name))
                        .findFirst()
                        .orElse(null);

        if (result == null)
            throw new InvalidRestaurantName();

        return result;
    }
    @Override
    public List<Restaurant> getRestaurantByType(String type) {
        return dataBase.getRestaurants().filter(i -> i.getType() == type).toList();
    }

    @Override
    public List<TableReservation> getReservationByUsername(String username) {
        return dataBase.getReservations()
                .filter(i -> i.getUsername().equals(username))
                .toList();
    }
    private boolean addressIsInInvalidFormat(Address address) {
        return Utils.isNullOrEmptyString(address.getCity())
                || Utils.isNullOrEmptyString(address.getCountry())
                || Utils.isNullOrEmptyString((address.getStreet()));
    }
}
