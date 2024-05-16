package controllers;

import interfaces.RestaurantService;
import models.RestaurantTable;
import models.TableReservation;
import models.TableReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.RestaurantServiceImpl;
import utils.AvailableTableInfo;
import utils.DTO.RestaurantTableDTO;
import utils.ReservationCancellationRequest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
public class TableController {
    private final RestaurantService service;
    public TableController() { service = RestaurantServiceImpl.getInstance(); }

    @PostMapping("/table")
    public ResponseEntity addTable(@RequestBody RestaurantTableDTO table) {
        try {
            service.addTable(table);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/DeleteReservation")
    public ResponseEntity cancelReservation(@RequestBody ReservationCancellationRequest reservation) {
        try {
            service.cancelReservation(reservation);
            return new ResponseEntity("reservation Removed",HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tables/restaurantName={name}")
    public ResponseEntity getTablesByRestaurantName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(service.getTablesByRestaurantName(name), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/reservations/restaurantName={name}")
    public ResponseEntity getReservationsByRestaurantName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(service.getReservationsByRestaurant(name), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reservations/username={username}")
    public ResponseEntity getReservationsByUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(service.getReservationByUsername(username), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    //ToDo fix mapping of this api
    @GetMapping("/tables/available/restaurantName={name},tableNumber={number},date={date}")
    public ResponseEntity<?> getAvailableTimesByRestaurantName(
            @PathVariable("name") String name,
            @PathVariable("number") int number,
            @PathVariable("date") String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            List<AvailableTableInfo> availableTimes = service.showAvailableTimes(name, date);
            return new ResponseEntity<>(availableTimes, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reserveTable")
    public ResponseEntity reserveTable(@RequestBody TableReservationDTO reservation) {
        try {
            service.reserveTable(reservation);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
}
