package com.user.service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.service.entities.Ratings;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

//    to test these APIs, go to src/test/java pakacge main class

    @GetMapping("/rating/get-rating-by-user-id")
    public List<Ratings> getRatingByUserId(@RequestParam(required = true) String userId);

    @PostMapping("/rating/add-rating")
    public Ratings addRating(Ratings rating);

//    update rating mehtod is not created in rating service controller
    @PutMapping("/rating/update-rating")
    public Ratings updateRating(Ratings rating);

//    delete rating mehtod is not created in rating service controller
    @DeleteMapping("/rating/delete-rating/{ratingId}")
    public void deleteRating(@PathVariable("ratingId") String ratingId);
}
