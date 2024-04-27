package controllers;

import interfaces.UserService;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.MizDooni;
import services.RestaurantServiceImpl;
import services.UserServiceImplementation;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private MizDooni mizDooni = MizDooni.getInstance();
    public void setMizdooni(MizDooni miz) {
        this.mizDooni = miz;
    }
    private final UserService service;
    public UserController() {
        service = UserServiceImplementation.getInstance();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        var user = service.getAllUsers().stream().filter(i -> i.getUsername().equals(username)).findFirst();

        if(user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return  new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        try {
            service.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage() + e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            service.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage() + e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        try {
            service.delete(username);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
}
