package com.springboot.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.springboot.demo.entity.User;

@Service
public class UserService {

    List<User> userList = new ArrayList<User>();

    @Cacheable(cacheNames = "userCache", key = "'User'")
    public User getUser() {
        return new User(101, "ashwin", "Male", "Botad");
    }

    @Cacheable(cacheNames = "userCache", key = "'User1'")
    public User getUser1() {
        return new User(101, "ashwin", "Male", "Mumbai");
    }

    @CachePut(cacheNames = "userCache", key = "'User'")
    public User updateUser() {
        return new User(101, "ashwin", "Male", "Mumbai");
    }

    @CacheEvict(cacheNames = "userCache", key = "'User'")
    public void deleteUser() {
    }

    @Cacheable(cacheNames = "usersCache", key = "'Users'")
    public List<User> getUsers() {
        userList.add(new User(1, "Alex", "Male", "Berlin"));
        userList.add(new User(2, "Tony", "Male", "Mexico"));
        userList.add(new User(3, "Andrew", "Male", "Chicago"));
        userList.add(new User(4, "Alexa", "Female", "Brussels"));
        userList.add(new User(5, "Maria", "Female", "Houston"));
        return userList;
    }
//    @Cacheable(cacheNames = "userCacheForId", key = "#id")
//    public User getUserById(Integer id) {
//        return user.get(id);
//    }

//    @CacheEvict(cacheNames = "userCache", allEntries = true)
//    public void deleteAllUsers() {
//    }

//    @Cacheable(cacheNames = "userPageCache", key = "'UserPage'")
//    @Cacheable(cacheNames = "userPageCache", key = "#pageRequest")
//    public Page<User> getUserPage(PageRequest pageRequest) {
////        PageRequest pageRequest = PageRequest.of(0, 10, JpaSort.unsafe("ASC", "name"));
//        return new PageImpl(userList, pageRequest, userList.size());
//    }

//    @Cacheable(cacheNames = "userListCache", key = "'UserList'")
//    public List<User> getUserList() {
//        return userList;
//    }
//
//    @Cacheable(cacheNames = "userMapCache", key = "'UserMap'")
//    public HashMap<Integer, User> getUserMap() {
//        return user;
//    }
}
