package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.WbsChatMessage;
import com.example.Comp1640.Entity.ChatMessage;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.ChatMessageRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.ChatMessageService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserService userService;

    public ChatMessageServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendMessageOneToOne(WbsChatMessage wbsChatMessage) {
        Long senderId = userService.findIdByUser(wbsChatMessage.getSender());
        Long recipientId = userService.findIdByUser(wbsChatMessage.getRecipient());
        ChatMessage chatMessage = new ChatMessage(senderId, recipientId, wbsChatMessage.getContent());
        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSendToUser(wbsChatMessage.getRecipient(), "/queue/messages", wbsChatMessage);
        System.out.println("📤 Sending message to user: " + wbsChatMessage.getRecipient());

    }

    @Override
    public List<WbsChatMessage> getOldMessages(Long user1Id, Long user2Id) {
        String user1;
        String user2;

        List<ChatMessage> chatMessages = chatMessageRepository.findChatHistory(user1Id, user2Id);
        List<WbsChatMessage> wbsChatMessages = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessages) {

            user1 = userService.findUserById(chatMessage.getSenderId()).get().getUsername();
            user2 = userService.findUserById(chatMessage.getRecipientId()).get().getUsername();

            wbsChatMessages.add(new WbsChatMessage( user1, user2,chatMessage.getContent()));

        }

        return wbsChatMessages;
    }
}
