package com.sertann.service;

import com.sertann.exceptions.UserExceptions;
import com.sertann.models.User;

import java.util.List;

public interface UserService {

    User registeUser(User user);

    User findUserById(Integer userId) throws UserExceptions;

    User findUserByEmail(String email);

    User followUser(Integer userId1, Integer userId2) throws UserExceptions;
    User unFollowUser(Integer userId1, Integer userId2) throws UserExceptions;

    User updateUser(User user, Integer userId) throws UserExceptions;

    List<User> searchUser(String query);

    User findUserByJwt(String jwt);

    int getFollowingsCount(Integer userId) throws UserExceptions;

    int getFollowersCount(Integer userId) throws UserExceptions;

    List<User> findLastFiveFollowings(Integer userId) throws UserExceptions;





}
