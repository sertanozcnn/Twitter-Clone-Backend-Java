package com.sertann.controller;


import com.sertann.models.Reels;
import com.sertann.models.User;
import com.sertann.service.ReelsService;
import com.sertann.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController {



    @Autowired
    private ReelsService reelsService;

    @Autowired
    private UserService userService;


    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt ){

        User reqUser = userService.findUserByJwt(jwt);
        return reelsService.createReel(reel,reqUser);

    }

    @GetMapping("/api/reels/user/{userId}")
    public ResponseEntity<List<Reels>> findUsersReels(@PathVariable Integer userId) throws Exception {
        List<Reels> reels = reelsService.findReelsByUserId(userId);
        return new ResponseEntity<List<Reels>>(reels, HttpStatus.OK);
    }


    @GetMapping("/api/reels")
    public List<Reels> findAllReels(){

        return reelsService.findAllReels();

    }



        @GetMapping("/api/reels/user/last-reels/{userId}")
        public ResponseEntity<List<Reels>> findLastReelsByUserId(
                @PathVariable Integer userId) {
            List<Reels> reels = reelsService.findLastReelsByUserId(userId);
            if (!reels.isEmpty()) {
                return new ResponseEntity<>(reels, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }




}
