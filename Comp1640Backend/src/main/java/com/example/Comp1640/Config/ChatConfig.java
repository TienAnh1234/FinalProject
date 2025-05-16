package com.example.Comp1640.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/user");
        // định nghĩa 2 kênh /topic và /queue, server sẽ gửi tin nhan tu hai kenh nay
        // frontend có thể subcribe để lấy tin nhắn
        registry.setApplicationDestinationPrefixes("/app");
        // định nghia kênh mà client có thể gửi tin nhắn lên server, thường dùng cho tin nhắn chung
        // frontend có thể publish để gửi
        registry.setUserDestinationPrefix("/user");
        // định nghĩa kênh mà server sẽ gửi tin nhan, thường dùng cho tin nhắn 1-1
        // dùng convertAndSendToUser tạo ra 1 đường dẫn riêng, frontend có thể subcribe để lấy tin nhắn
        // ko cần qua sendto
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:4200")
                .withSockJS();
    }


}
