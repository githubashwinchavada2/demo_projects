package com.rating.service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rating.service.entities.Ratings;
import com.rating.service.service.RatingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
public class RatingController {

    public final RatingService ratingService;

//    http://localhost:8020/rating

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(value = "/add-rating", consumes = "application/json")
    public ResponseEntity<Ratings> addRating(@RequestBody Ratings rating) {
        Ratings addedUser = ratingService.addRating(rating);
        return ResponseEntity.ok(addedUser);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping(value = "/get-rating-by-user-id", produces = "application/json")
    public ResponseEntity<List<Ratings>> getRatingByUserId(@RequestParam("userId") String userId) {
        List<Ratings> fetchedRatings = ratingService.getRatingByUserId(userId);
        return ResponseEntity.ok(fetchedRatings);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping(value = "/get-rating-by-hotel-id", produces = "application/json")
    public ResponseEntity<List<Ratings>> getRatingByHotelId(@RequestParam("hotelId") String hotelId) {
        List<Ratings> fetchedRatings = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.ok(fetchedRatings);
    }
}
