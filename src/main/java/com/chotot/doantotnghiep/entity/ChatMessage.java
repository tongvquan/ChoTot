package com.chotot.doantotnghiep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chatmessage")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatid")
    private String chatId;

    @Column(name = "senderid")
    private Long senderId;

    @Column(name = "recipientid")
    private Long recipientId;

    @Column(name = "content")
    private String content;

    private Date timestamp;
}
