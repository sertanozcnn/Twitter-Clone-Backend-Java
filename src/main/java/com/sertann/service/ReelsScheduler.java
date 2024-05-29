package com.sertann.service;


import com.sertann.models.Reels;
import com.sertann.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReelsScheduler {

    /*private final ReelsService reelsService;
    @Autowired
    UserService userService;


    @Autowired
    public ReelsScheduler(ReelsService reelsService, UserService userService) {
        this.reelsService = reelsService;
        this.userService = userService;
    }


    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    public void deleteExpiredReels(){
        Integer userId = userService.findUserByJwt()
        LocalDateTime now = LocalDateTime.now();
        List<Reels> reels = reelsService.findAllReels();
        for(Reels reel : reels){
            LocalDateTime createdAt = reel.getCreatedAt();
            LocalDateTime expirationTime = createdAt.plusHours(12);
            if(now.isAfter(expirationTime)){
                reelsService.deleteReels(reel.getId())
            }
        }
    }*/

}
