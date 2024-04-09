package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.ChatMessage;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    List<ChatMessage> findByid(Long s);
    List<ChatMessage> findAllBySenderAndRecipient(UserEntity sender, UserEntity recipient);

}
