package com.sertann.service;


import com.sertann.models.Story;
import com.sertann.models.User;
import com.sertann.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StoryServiceImplementation implements StoryService{


    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;



    @Override
    public Story createStory(Story story, User userId) {


        Story createdStory = new Story();

        createdStory.setCaptions(story.getCaptions());
        createdStory.setImage(story.getImage());
        createdStory.setUser(userId);
        createdStory.setTimestamp(story.getTimestamp());

        return storyRepository.save(createdStory);


    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception {

            User user = userService.findUserById(userId);
            return storyRepository.findByUserId(userId);


    }
}
