package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.entity.ChatMessage;

import java.util.List;

public interface IChatMessageService {
    ChatMessage save(ChatMessage chatMessage);
    List<ChatMessage> findChatMessages(Long senderId, Long recipientId);
}
