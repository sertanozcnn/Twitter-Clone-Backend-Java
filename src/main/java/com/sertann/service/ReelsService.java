package com.sertann.service;

import com.sertann.models.Post;
import com.sertann.models.Reels;
import com.sertann.models.User;

import java.util.List;

public interface ReelsService {


    Reels createReel(Reels reel , User user);

    List<Reels> findAllReels();

    List<Reels> findUserReels(Integer userId) throws Exception;


    List<Reels> findReelsByUserId(Integer userId);

    Reels findReelsById(Integer reelsId) throws Exception;
    String deleteReels(Integer reelsId,Integer userId) throws Exception;



    List<Reels> findLastReelsByUserId(Integer userId);







}
