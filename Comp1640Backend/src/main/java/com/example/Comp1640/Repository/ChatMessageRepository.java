package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query(value = "SELECT * FROM chat_message " +
            "WHERE (sender_id = :user1Id AND recipient_id = :user2Id) " +
            "   OR (sender_id = :user2Id AND recipient_id = :user1Id) ", nativeQuery = true)
    List<ChatMessage> findChatHistory(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);



}
