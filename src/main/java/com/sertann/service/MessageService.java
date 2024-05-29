package com.sertann.service;

import com.sertann.models.Chat;
import com.sertann.models.Message;
import com.sertann.models.User;

import java.util.List;

public interface MessageService {

    Message createMessage(User user, Integer chatId, Message req) throws Exception;

    List<Message> findChatsMessage(Integer chatId) throws Exception;

}
