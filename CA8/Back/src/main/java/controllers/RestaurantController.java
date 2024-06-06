package controllers;

import interfaces.RestaurantService;
import models.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.RestaurantServiceImpl;
import utils.DTO.RestaurantDTO;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;

import java.util.List;

@RestController
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

    @GetMapping("/name={name}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable("name") String name) {
        var transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("getRestaurantByName");
            transaction.setType("request");
            return new ResponseEntity<>(service.getRestaurantByName(name), HttpStatus.OK);
        }
        catch (Exception e) {
            transaction.captureException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        finally {
            transaction.end();
        }
    }

    @GetMapping("/RestaurantName={name}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByName(@PathVariable("name") String name) {
        var transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("getRestaurantsByName");
            transaction.setType("request");
            return new ResponseEntity<>(service.getRestaurantsByName(name), HttpStatus.OK);
        }
        catch (Exception e) {
            transaction.captureException(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        finally {
            transaction.end();
        }
    }

    @GetMapping("/managerName={name}")
    public ResponseEntity<List<Restaurant>> getRestaurantByManagerName(@PathVariable("name") String name) {
        var transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("getRestaurantByManagerName");
            transaction.setType("request");
            return new ResponseEntity<>(service.getRestaurantsByManager(name), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        finally {
            transaction.end();
        }
    }

    @GetMapping("/type={type}")
    public ResponseEntity<List<Restaurant>> getRestaurantByType(@PathVariable("type") String type) {
        var transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("getRestaurantByType");
            transaction.setType("request");
            return new ResponseEntity<>(service.getRestaurantByType(type), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        finally {
            transaction.end();
        }
    }

    @PostMapping
    public ResponseEntity createRestaurant(@RequestBody RestaurantDTO restaurantData) {
        var transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("createRestaurant");
            transaction.setType("request");
            service.addRestaurant(restaurantData);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage() + e.getStackTrace(), HttpStatus.NOT_FOUND);
        }
        finally {
            transaction.end();
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteRestaurant(@PathVariable("name") String name) {
        var transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("deleteRestaurant");
            transaction.setType("request");
            service.delete(name);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        finally {
            transaction.end();
        }
    }

}
