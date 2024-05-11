package controllers;

import interfaces.FeedbackService;
import models.Feedback;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.FeedbackServiceImpl;

@RestController
@RequestMapping("/reviews")
public class FeedbackController {
    private final FeedbackService service;
    public FeedbackController() { service = FeedbackServiceImpl.getInstance(); }

    @PostMapping
    public ResponseEntity addFeedback(@RequestBody Feedback feedback) {
        try {
            service.addReview(feedback);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage() + "\n" + e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/restaurantName={name}")
    public ResponseEntity restaurantName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(service.getReviewsByRestaurantName(name), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage() + "\n" + e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
}
