package controllers;

import interfaces.RestaurantService;
import models.Table;
import models.TableReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.RestaurantServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TableController {
    private final RestaurantService service;
    public TableController() { service = RestaurantServiceImpl.getInstance(); }

    @PostMapping("/table")
    public ResponseEntity addTable(@RequestBody Table table) {
        try {
            service.addTable(table);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reservation")
    public ResponseEntity addReservation(@RequestBody TableReservation reservation) {
        try {
            return new ResponseEntity(service.reserveTable(reservation), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reservation")
    public ResponseEntity cancelReservation(@RequestBody TableReservation reservation) {
        try {
            service.cancelReservation(reservation);
            return new ResponseEntity(HttpStatus.OK);
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
    @GetMapping("/tables/available/restaurnatName={name},date={date}")
    public ResponseEntity getAvailableTimesByRestaurantName(@PathVariable("name") String name, @PathVariable("date") String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            return new ResponseEntity(service.getAvailableTimesByRestaurant(name, date), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity(ex.getMessage() + "\n" + ex.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
}
