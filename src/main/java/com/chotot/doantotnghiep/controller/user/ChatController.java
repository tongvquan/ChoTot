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

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage){
        System.out.println(123);
        ChatMessage saveMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(),"/queue/messages",
                ChatNotification.builder()
                        .id(saveMsg.getId())
                        .senderId(saveMsg.getSenderId())
                        .recipientId(saveMsg.getRecipientId())
                        .content(saveMsg.getContent())
                        .build()
        );
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public UserEntity addUser(@Payload UserEntity user){
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public UserEntity disconnect(@Payload UserEntity user){
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> findConnectedUsers(){
        return ResponseEntity.ok(userService.findConnectesUsers());
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId")Long senderId, @PathVariable("recipientId")Long recipientId){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId,recipientId));
    }

}
