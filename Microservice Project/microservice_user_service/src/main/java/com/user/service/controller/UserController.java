package com.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entities.User;
import com.user.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/user")
//@AllArgsConstructor
@Log4j2
public class UserController {

//  http://localhost:8010/user

    private Integer retryCount;
    private final UserService userService;

    public UserController(
            @Value("${retryCount}") Integer retryCount,
            UserService userService) {
        this.retryCount = retryCount;
        this.userService = userService;
    }

    @PostMapping(value = "/add-user", consumes = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

//    URL to check health: go to service registry-> open service url -> you will see URL like this- http://192.168.114.91:8010/actuator/info ->
//    replace /info in URL with /health -> new URL will be like this- http://192.168.114.91:8010/actuator/health

    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
//    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    @GetMapping(value = "/get-user", produces = "application/json")
    public ResponseEntity<User> getUser(@RequestParam("emailId") String emailId) {
//        log.info("retry Count: " + retryCount);
//        retryCount++;

        User fetchedUser = userService.getUser(emailId);
        return ResponseEntity.ok(fetchedUser);
    }

    public ResponseEntity<User> ratingHotelFallback(@RequestParam("emailId") String emailId, Exception ex) {
        log.info("Fallback method exception stack trace:::");
        ex.printStackTrace();
        log.info("Fallback method is called because service is down: " + ex.getMessage());
        User user = User.builder()
                .userId("12345")
                .userName("Dummy Username")
                .emailId("Dummy@email.com")
                .role("DummyRole")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "/get-all-users", produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> fetchedUsers = userService.getAllUsers();
        return ResponseEntity.ok(fetchedUsers);
    }
}
