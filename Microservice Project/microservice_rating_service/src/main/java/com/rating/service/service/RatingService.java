package com.rating.service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rating.service.entities.Ratings;
import com.rating.service.repository.RatingRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Ratings addRating(Ratings rating) {
        String randomRatingId = UUID.randomUUID().toString();
        rating.setRatingId(randomRatingId);
        rating = ratingRepository.save(rating);
        return rating;
    }

    public List<Ratings> getRatingByUserId(String userId) {
        List<Ratings> ratings = ratingRepository.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find ratings with user id: " + userId);
        }
        return ratings;
    }

    public List<Ratings> getRatingByHotelId(String hotelId) {
        List<Ratings> ratings = ratingRepository.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find hotel with user id: " + hotelId);
        }
        return ratings;
    }
}
