package controllers;

import interfaces.RestaurantService;
import models.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.MizDooni;
import services.RestaurantServiceImpl;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService service;
    public RestaurantController() {
        service = RestaurantServiceImpl.getInstance();
    }
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(service.fetchAll(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(service.getRestaurantByName(name), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createRestaurant(@RequestBody Restaurant restaurantData) {
        try {
            service.addRestaurant(restaurantData);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage() + e.getStackTrace(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{name}")
    public ResponseEntity updateRestaurant(@PathVariable("name") String name, @RequestBody Restaurant updatedRestaurantData) {
        try {
            service.updateRestaurant(updatedRestaurantData);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteRestaurant(@PathVariable("name") String name) {
        try {
            service.delete(name);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
