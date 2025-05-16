package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.AddFriendRequestDto;
import com.example.Comp1640.DTO.WbsChatMessage;
import com.example.Comp1640.Service.ChatMessageService;
import com.example.Comp1640.Service.ServiceImpl.AddFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class AddFriendRequestController {

    @Autowired
    private AddFriendService addFriendService;

    @MessageMapping("addFriend.sendRequest")  // Maps messages sent to "addFriend.sendRequest" WebSocket destination
    public void sendAddFriendRequest(@Payload AddFriendRequestDto addFriendRequestDto) {
        addFriendService.sendRequest(addFriendRequestDto);
    }

    @MessageMapping("addFriend.acceptRequest")  // Maps messages sent to "addFriend.sendRequest" WebSocket destination
    public void acceptRequest(@Payload AddFriendRequestDto addFriendRequestDto) {
        addFriendService.acceptRequest(addFriendRequestDto);
    }



}
