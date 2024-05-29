package com.sertann.service;

import com.sertann.models.Story;
import com.sertann.models.User;

import java.util.List;

public interface StoryService {

    Story createStory(Story story, User userId);

    List<Story> findStoryByUserId(Integer userId) throws Exception;




}
