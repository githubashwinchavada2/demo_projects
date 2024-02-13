package com.springboot.demo.controller;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.entity.User;
import com.springboot.demo.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private CacheManager cacheManager;
    public final UserService userService;

    public Cache getEhCache(String cacheName) {
        Stream<String> cacheNames = getStreamFromIterable(cacheManager.getCacheNames());
        return cacheNames
                .filter(c -> cacheManager.getCache(c).getName().equalsIgnoreCase(cacheName))
                .map(c -> cacheManager.getCache(c))
                .findFirst().orElse(null);
    }
    public void getEhCache(String cacheName, String key) {
        Stream<String> cacheNames = getStreamFromIterable(cacheManager.getCacheNames());
        try {
            cacheNames.forEach(c -> {
                if (cacheManager.getCache(c).getName().equalsIgnoreCase(cacheName)) {
                    System.out.println("\nEhCache::: " + cacheManager.getCache(cacheName).getName()
                            + " : " + cacheManager.getCache(cacheName).get(key).get() + "\n");
                }
            });
        } catch (Exception e) {
            System.out.println("\nEhCache::: " + cacheManager.getCache(cacheName).getName()
                    + " : empty" + "\n");
        }
    }

    public void getEhCache1() {
        Stream<String> cacheNames = getStreamFromIterable(cacheManager.getCacheNames());
        cacheNames.forEach(cacheName -> {
                try {
                    System.out.println("\nEhCaches::: " + 
                            cacheManager.getCache(cacheName).getNativeCache() + "\n"
                            + cacheManager.getCache(cacheName).getName() + "\n"
//                            + cacheManager.getCache(cacheName).get(1).get() + "\n"
//                            + cacheManager.getCache(cacheName).get("User1").get() + "\n"
                            + cacheManager.getCache(cacheName).get("User").get() + "\n"
                            + cacheManager.getCache(cacheName).get("Users").get() + "\n"
//                            + cacheManager.getCache(cacheName).get("UserList").get() + "\n"
//                            + cacheManager.getCache(cacheName).get("UserMap").get() + "\n"
                            );
                } catch (Exception e) {
                    System.out.println("\nEhCaches::: " + cacheManager.getCache(cacheName).getName() + " is empty");
                }
            }
        );
    }

    public static <T> Stream<T> getStreamFromIterable(Iterable<T> iterable) {
        Spliterator<T> spliterator = iterable.spliterator();
        return StreamSupport.stream(spliterator, false);
    }


//    http://localhost:8080/get-all-users

//    @GetMapping(value = "/get-all-users", produces = "application/json")
//    public ResponseEntity<List<User>> getUser() {
//        List<User> fetchedUsers = userService.getUser();
//        return ResponseEntity.ok(fetchedUsers);
//    }

    @GetMapping(value = "/get-user", produces = "application/json")
    public ResponseEntity<User> getUser() {
        User user = userService.getUser();

        getEhCache("userCache", "User");
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/get-user1", produces = "application/json")
    public ResponseEntity<User> getUser1() {
        User user = userService.getUser1();

        getEhCache("userCache", "User");
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/update-user", produces = "application/json")
    public ResponseEntity<User> getUpdateUser() {
        User user = userService.updateUser();

        getEhCache("userCache", "User");
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/delete-user")
    public void getDeleteUser() {
        userService.deleteUser();

        getEhCache("userCache", "User");
    }


    @GetMapping(value = "/get-users", produces = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();

        getEhCache("usersCache", "Users");
        return ResponseEntity.ok(users);
    }
}
