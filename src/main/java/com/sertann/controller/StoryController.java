package com.sertann.controller;


import com.sertann.models.Story;
import com.sertann.models.User;
import com.sertann.service.StoryService;
import com.sertann.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {


    @Autowired
    private StoryService storyService;


    @Autowired
    private UserService userService;
    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt){


        User reqUser = userService.findUserByJwt(jwt);

        Story createdStory = storyService.createStory(story,reqUser);

        return createdStory;

    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findUsersStory(@PathVariable Integer userId ,@RequestHeader("Authorization") String jwt) throws Exception {


        User reqUser = userService.findUserByJwt(jwt);

        List<Story> stories = storyService.findStoryByUserId(userId);


        return stories;

    }


}
