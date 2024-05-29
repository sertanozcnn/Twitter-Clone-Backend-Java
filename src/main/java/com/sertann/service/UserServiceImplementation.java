package com.sertann.service;

import com.sertann.config.JwtProvider;
import com.sertann.exceptions.UserExceptions;
import com.sertann.models.User;
import com.sertann.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User registeUser(User user) {


        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());
        newUser.setGender(user.getGender());
        newUser.setId(user.getId());
        newUser.setContent(user.getContent());
        newUser.setRandomProfileColorCode(user.getRandomProfileColorCode());
        newUser.setImage(user.getImage());
        newUser.setBackgroundImage(user.getBackgroundImage());
        newUser.setNickname(user.getNickname());

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserExceptions {
        //Exception if the userid from the api is not found
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new UserExceptions("User not exist with user id "+ userId));

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws UserExceptions {

        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        user2.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(user2.getId());
        if(reqUser.getId()==user2.getId()){
            throw new UserExceptions("You can't follow yourself");
        }

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;

    }

    @Override
    public User unFollowUser(Integer reqUserId, Integer userId2) throws UserExceptions {
        //We take the uses own
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);
        user2.getFollowers().remove(reqUser.getId());//We should also remove it from the users follower list
        reqUser.getFollowings().remove(user2.getId()); //We remove the user from their list
        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
    }


    @Override
    public User updateUser(User user, Integer userId) throws UserExceptions {
        Optional<User> user1 = userRepository.findById(userId);

        if (user1.isEmpty()) {
            throw new UserExceptions("User does not exist with id " + userId);
        }
        User oldUser = user1.get();
        if (user.getFirstName() != null) {
            oldUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            oldUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            oldUser.setGender(user.getGender());
        }
        if (user.getContent() != null) {
            oldUser.setContent(user.getContent());
        }
        if (user.getNickname() != null) {
            oldUser.setNickname(user.getNickname());
        }
        if (user.getRandomProfileColorCode() != null) {
            oldUser.setRandomProfileColorCode(user.getRandomProfileColorCode());
        }
        if (user.getImage() != null) {
            oldUser.setImage(user.getImage());
        }
        if (user.getBackgroundImage() != null) {
            oldUser.setBackgroundImage(user.getBackgroundImage());
        }


        return userRepository.save(oldUser);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {

        String email = JwtProvider.getEmailFromJwtToken(jwt);
        return userRepository.findByEmail(email);
    }

    @Override
    public int getFollowingsCount(Integer userId) throws UserExceptions {
        User user = findUserById(userId);
        return user.getFollowings().size();
    }

    @Override
    public int getFollowersCount(Integer userId) throws UserExceptions {
        User user = findUserById(userId);
        return user.getFollowers().size();
    }

    @Override
    public List<User> findLastFiveFollowings(Integer userId) throws UserExceptions {
        User user = findUserById(userId);
        List<Integer> followings = user.getFollowings();
        List<User> lastFiveFollowings = new ArrayList<>();

        // Get the last 5 users of the followed list
        int startIndex = Math.max(0, followings.size() - 5);
        for (int i = startIndex; i < followings.size(); i++) {
            User followingUser = findUserById(followings.get(i));
            lastFiveFollowings.add(followingUser);
        }

        return lastFiveFollowings;
    }


}
