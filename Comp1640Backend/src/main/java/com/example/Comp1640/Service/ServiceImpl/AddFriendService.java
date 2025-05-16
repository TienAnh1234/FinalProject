package com.example.Comp1640.Service.ServiceImpl;


import com.example.Comp1640.DTO.AddFriendRequestDto;
import com.example.Comp1640.DTO.WbsChatMessage;
import com.example.Comp1640.Entity.AddFriendRequest;
import com.example.Comp1640.Entity.ChatMessage;
import com.example.Comp1640.Repository.AddFriendRequestRepository;
import com.example.Comp1640.Service.ClassroomService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddFriendService {

    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    private AddFriendRequestRepository addFriendRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomService classroomService;


    public AddFriendService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendRequest(AddFriendRequestDto addFriendRequestDto) {
        List<AddFriendRequest> allAddFriendRequests = addFriendRequestRepository.findAll();


        Long senderId = userService.findIdByUser(addFriendRequestDto.getSender());
        Long recipientId = userService.findIdByUser(addFriendRequestDto.getRecipient());

        boolean exists = allAddFriendRequests.stream()
                .anyMatch(req -> req.getSenderId().equals(senderId)
                        && req.getRecipientId().equals(recipientId) ||
                        req.getSenderId().equals(recipientId) && req.getRecipientId().equals(senderId));
        if(exists) {

        }else {
            AddFriendRequest addFriendRequest = new AddFriendRequest(senderId, recipientId, addFriendRequestDto.getContent(), addFriendRequestDto.getStatus());
            AddFriendRequest savedRequest = addFriendRequestRepository.save(addFriendRequest);

            addFriendRequestDto.setId(savedRequest.getId());
            messagingTemplate.convertAndSendToUser(addFriendRequestDto.getRecipient(), "/queue/addFriendRequest", addFriendRequestDto);
        }
    }

    public List<AddFriendRequestDto> getAddFriendRequestIReceived(Long recipientId) {
        String sender;
        String recipient;

        List<AddFriendRequest> addFriendRequests = addFriendRequestRepository.findMyRequestIReceived( recipientId);
        List<AddFriendRequestDto> addFriendRequestDtos = new ArrayList<>();

        for (AddFriendRequest addFriendRequest : addFriendRequests) {

            sender = userService.findUserById(addFriendRequest.getSenderId()).get().getUsername();
            recipient = userService.findUserById(addFriendRequest.getRecipientId()).get().getUsername();

            addFriendRequestDtos.add(new AddFriendRequestDto(addFriendRequest.getId(), sender, recipient,addFriendRequest.getContent(), addFriendRequest.getStatus()));

        }

        return addFriendRequestDtos;
    }

    public void deleteAddFriendRequest(Long id) {
        addFriendRequestRepository.deleteById(id);
    }

    public void acceptRequest(AddFriendRequestDto addFriendRequestDto) {
        if(Objects.equals(addFriendRequestDto.getStatus(), "APPROVED")){
            classroomService.saveClassroomMain(addFriendRequestDto);
            deleteAddFriendRequest(addFriendRequestDto.getId());
            messagingTemplate.convertAndSendToUser(addFriendRequestDto.getSender(), "/queue/acceptFriendRequest", addFriendRequestDto);
        }else{
            deleteAddFriendRequest(addFriendRequestDto.getId());
            messagingTemplate.convertAndSendToUser(addFriendRequestDto.getSender(), "/queue/acceptFriendRequest", addFriendRequestDto);
        }
    }

}
