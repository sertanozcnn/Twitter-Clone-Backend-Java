package com.sertann.controller;


import com.sertann.exceptions.UserExceptions;
import com.sertann.models.User;
import com.sertann.repository.UserRepository;
import com.sertann.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    //All Users
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //Get User By Id
    @GetMapping("/api/users/{userId}")
    public User getUserById(
            @PathVariable("userId") Integer id) throws UserExceptions {

        return userService.findUserById(id);


    }


    //Update User Field
    @PutMapping("/api/users")
    public User updateUser(
            @RequestHeader("Authorization") String jwt,
            @RequestBody User user) throws UserExceptions {

        User reqUser = userService.findUserByJwt(jwt);
        return userService.updateUser(user, reqUser.getId());

    }
    //Brings users with more than five followers
    @GetMapping("/api/users/get-all-popular-users")
    public ResponseEntity<List<User>> getAllPopularUsers(
            @RequestHeader("Authorization") String jwt){

        User reqUser = userService.findUserByJwt(jwt);
        List<User> getAllPopular = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers){
            if(user.getFollowers().size() >= 5 && !user.getFollowers().contains(reqUser.getId())){
                getAllPopular.add(user);
            }
        }
        return new ResponseEntity<>(getAllPopular,HttpStatus.OK);
    }


    //User Follow
    @PutMapping("/api/users/follow/{userId2}")
    public User followUserHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer userId2) throws UserExceptions {
        User reqUser = userService.findUserByJwt(jwt);

        return userService.followUser(reqUser.getId(), userId2);
    }

    //User Unfollow
    @PutMapping("/api/users/unfollow/{userId2}")
    public User unfollowUserHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer userId2) throws UserExceptions {

        User reqUser = userService.findUserByJwt(jwt);
        return userService.unFollowUser(reqUser.getId(), userId2);

    }


    //User search query
    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userService.searchUser(query);
    }


    //Retrieve the information of the tokenized user
    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }


    @GetMapping("/api/users/followingscount/{userId}")
    public ResponseEntity<Integer> getFollowingsCount(@PathVariable Integer userId) throws UserExceptions {

        int followingsCount = userService.getFollowingsCount(userId);
        return new ResponseEntity<>(followingsCount, HttpStatus.OK);

    }

    @GetMapping("/api/users/followerscount/{userId}")
    public ResponseEntity<Integer> getFollowersCount(@PathVariable Integer userId) throws UserExceptions {

        int followersCount = userService.getFollowersCount(userId);
        return new ResponseEntity<>(followersCount, HttpStatus.OK);

    }

    //Information about the user's followings
    @GetMapping("/api/users/following-details/{userId}")
    public ResponseEntity<List<User>> getFollowingDetails(@PathVariable Integer userId) throws UserExceptions {

        List<User> followingDetails = new ArrayList<>();
        User user = userService.findUserById(userId);
        for (Integer followingId : user.getFollowings()) {
            User followingUser = userService.findUserById(followingId);
            followingDetails.add(followingUser);
        }

        return new ResponseEntity<>(followingDetails, HttpStatus.OK);
    }


    //Information about the user's followers
    @GetMapping("/api/users/followers-details/{userId}")
    public ResponseEntity<List<User>> getFollowersDetails(@PathVariable Integer userId) throws UserExceptions {
        List<User> followersDetails = new ArrayList<>();
        User user = userService.findUserById(userId);
        for (Integer followersId : user.getFollowers()) {
            User followersUser = userService.findUserById(followersId);
            followersDetails.add(followersUser);
        }
        return new ResponseEntity<>(followersDetails, HttpStatus.OK);
    }



    //Get the user's last five followed
    @GetMapping("/api/users/last-five-followings")
    public ResponseEntity<List<User>> getLastFiveFollowings(@RequestHeader("Authorization") String jwt) {
        User reqUser = userService.findUserByJwt(jwt);
        try {
            List<User> lastFiveFollowings = userService.findLastFiveFollowings(reqUser.getId());
            return new ResponseEntity<>(lastFiveFollowings, HttpStatus.OK);
        } catch (UserExceptions e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
