package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.repository.ChatMessageRepository;
import com.chotot.doantotnghiep.entity.ChatMessage;
import com.chotot.doantotnghiep.repository.UserRepository;
import com.chotot.doantotnghiep.service.impl.IChatMessageService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService implements IChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Override
    public ChatMessage save(String content, Long id) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(userService.findByUserName(SecurityUtils.getCurrentUserName()));
        chatMessage.setRecipient(userService.findById(id));
        chatMessage.setContent(content);
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        UserEntity sender = userRepository.findById(senderId).get();
        UserEntity recipient = userRepository.findById(recipientId).get();
        return chatMessageRepository.findAllBySenderAndRecipient(sender,recipient);
    }


}
