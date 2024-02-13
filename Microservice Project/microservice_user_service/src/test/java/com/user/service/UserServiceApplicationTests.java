package com.user.service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.service.entities.Ratings;
import com.user.service.external.services.RatingService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class UserServiceApplicationTests {

    @Autowired
    private RatingService ratingService;

    @Test
    void contextLoads() {
    }

    @Test
    public void addRating() {
        Ratings rating = Ratings.builder()
                .ratingId(UUID.randomUUID().toString())
                .userId("2be64c74-89a8-4c45-8164-c276647fbe38")
                .hotelId("51820987-685b-401c-a845-06abbc8ec488")
                .rating(3)
                .feedback("nice hotel")
                .build();

        Ratings savedRating = ratingService.addRating(rating);
        log.info("savedRating::: " + savedRating);
    }
}
