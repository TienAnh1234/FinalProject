package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.WbsChatMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ChatMessageService {

    void sendMessageOneToOne(@Payload WbsChatMessage wbsChatMessage);

    List<WbsChatMessage> getOldMessages(@PathVariable Long user1Id, @PathVariable Long user2Id );
}
