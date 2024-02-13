package com.user.service.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.user.service.entities.Hotel;
import com.user.service.entities.Ratings;
import com.user.service.entities.User;
import com.user.service.external.services.HotelService;
import com.user.service.external.services.RatingService;
import com.user.service.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private RestTemplate restTemplate;
    private UserRepository userRepository;
    private HotelService hotelService;
    private RatingService ratingService;

    public User addUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        user.setPassword(user.getPassword());
        user.setRole("ROLE_" + user.getRole().toUpperCase());
        user = userRepository.save(user);
        return user;
    }

    public User getUser(String emailId) {
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not find user with email id: " + emailId));

        /** fething ratings from ratings service */

//        ResponseEntity<List<Ratings>> userRatings = restTemplate.exchange(
////                "http://localhost:8030/rating/get-rating-by-user-id?userId=" + user.getUserId(),
//                "http://RATING-SERVICE/rating/get-rating-by-user-id?userId=" + user.getUserId(),
////                using service name registered in service registry instead of hostname and port. if host or port gets changed it won't impact
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Ratings>>(){});
//        log.info("ratings::: " + userRatings.getBody());

        List<Ratings> ratings = ratingService.getRatingByUserId(user.getUserId());
//        List<Ratings> ratings = userRatings.getBody();
        user.setRatings(ratings);

        ratings.stream()
            .map(rating -> {
                /** fething hotel from hotel service */

//                ResponseEntity<Hotel> ratingsOfHotel = restTemplate.exchange(
////                        "http://localhost:8020/hotel/get-hotel?hotelId=" + rating.getHotelId(),
//                        "http://HOTEL-SERVICE/hotel/get-hotel?hotelId=" + rating.getHotelId(),
////                        using service name registered in service registry instead of hostname and port. if host or port gets changed it won't impact
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<Hotel>(){});
//                log.info("hotels::: " + ratingsOfHotel.getBody());

//                Hotel hotel = ratingsOfHotel.getBody();
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);

                return rating;
            })
            .collect(Collectors.toList());

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find users");
        }
        return users;
    }
}
