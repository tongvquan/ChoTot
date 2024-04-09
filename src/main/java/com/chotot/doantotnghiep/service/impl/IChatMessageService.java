package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.entity.ChatMessage;

import java.util.List;

public interface IChatMessageService {
    ChatMessage save(String content, Long id);
    List<ChatMessage> findChatMessages(Long senderId, Long recipientId);
}
