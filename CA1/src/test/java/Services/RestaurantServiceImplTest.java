package Services;

import enums.UserType;
import models.*;
import exceptions.*;
import interfaces.DataBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.RestaurantServiceImpl;
import DataBase.MemoryDataBase;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class RestaurantServiceImplTest {

    private RestaurantServiceImpl restaurantService;
    private DataBase dataBase;

    @BeforeEach
    void setUp() {
        dataBase = new MemoryDataBase();
        restaurantService = new RestaurantServiceImpl(dataBase);
        dataBase.saveUser(new User( UserType.manager, "user1", "1234", "email@yahoo.com", new UserAddress("hh","Tehran")));
        dataBase.saveUser(new User( UserType.client, "user2", "1234", "email@yahoo.com", new UserAddress("hh","Tehran")));
        dataBase.saveRestaurant(new Restaurant( "restaurant1", "manager", "type", new Date("2024/3/12 12:00:00"), new Date("2024/9/12 19:00:00"), "description", new RestaurantAddress("hh","Tehran","Kargar")));
        dataBase.saveTable(new Table(1, "restaurant1","user2", 4));
    }

    @Test
    void reserveTable_successfulReservation() throws Exception {
        TableReservation tableReservation = new TableReservation(1,"user2", "restaurant1",1 , new Date("2024/3/13 15:00:00"));
        restaurantService.reserveTable(tableReservation);
        assertEquals(1, dataBase.getReservations().count());
    }

    @Test
    void reserveTable_managerRoleAttempt() {
        TableReservation tableReservation = new TableReservation(1,"user1", "restaurant1",1 , new Date("2024/3/13 15:00:00"));
        assertThrows(RoleException.class, () -> restaurantService.reserveTable(tableReservation));
    }
    @Test
    void restaurantNotFound() {
        TableReservation tableReservation = new TableReservation(1,"user2", "restaurant2",1 , new Date("2024/3/13 15:00:00"));
        assertThrows(RestaurantNotFound.class, () -> restaurantService.reserveTable(tableReservation));
    }
    @Test
    void reserveTable_invalidTableNumber() {
        TableReservation tableReservation = new TableReservation(1,"user2", "restaurant1",2 , new Date("2024/3/13 15:00:00"));
        assertThrows(TableNotFoundException.class, () -> restaurantService.reserveTable(tableReservation));
    }

    @Test
    void outside_BusinessHours() {
        TableReservation tableReservation = new TableReservation(1,"user2", "restaurant1",1 , new Date("2024/3/13 3:00:00"));
        assertThrows(OutsideBusinessHoursException.class, () -> restaurantService.reserveTable(tableReservation));
    }

    @Test
    void pastDate() {
        TableReservation tableReservation = new TableReservation(1,"user2", "restaurant1",1 , new Date("2021/3/13 15:00:00"));
        assertThrows(PastDateTimeException.class, () -> restaurantService.reserveTable(tableReservation));
    }

    @Test
    void tableAlreadyReserved() throws Exception{
        TableReservation tableReservation_1 = new TableReservation(1,"user2", "restaurant1",1 , new Date("2024/3/13 15:00:00"));
        TableReservation tableReservation_2 = new TableReservation(1,"user2", "restaurant1",1 , new Date("2024/3/13 15:00:00"));
        restaurantService.reserveTable(tableReservation_1);
        assertThrows(TimeSlotAlreadyBookedException.class, () -> restaurantService.reserveTable(tableReservation_2));
    }
    @AfterEach
    void tearDown() {
        dataBase = null;
        restaurantService = null;
    }

}
