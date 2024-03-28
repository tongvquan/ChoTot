package com.chotot.doantotnghiep.service.impl;

import java.util.Optional;

public interface IChatRoomService {
    Optional<String> getChatRoomId(Long senderId, Long recipientId, Boolean createNewRoomIfNotExists);
}
