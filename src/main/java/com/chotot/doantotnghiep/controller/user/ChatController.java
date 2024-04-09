package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.chat.ChatNotification;
import com.chotot.doantotnghiep.entity.ChatMessage;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.service.impl.IChatMessageService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @PostMapping("/chat")
    public void processMessage(@RequestParam("content") String content, Long id){
        ChatMessage saveMsg = chatMessageService.save(content, id);
    }




    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId")Long senderId, @PathVariable("recipientId")Long recipientId){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId,recipientId));
    }

}
