package com.sertann.controller;


import com.sertann.models.Chat;
import com.sertann.models.User;
import com.sertann.request.CreateChatRequest;
import com.sertann.service.ChatService;
import com.sertann.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @PostMapping("/api/chats")
    public Chat createChat(
            @RequestHeader("Authorization")String jwt ,
            @RequestBody CreateChatRequest req) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(req.getUserId());

        Chat chat = chatService.createChat(reqUser, user2);
        return chat;
    }

    @GetMapping("/api/chats")

    public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt) {

        User user = userService.findUserByJwt(jwt);

        List<Chat> chats = chatService.findUsersChat(user.getId());

        return chats;
    }


}
