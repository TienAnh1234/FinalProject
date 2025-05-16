package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.*;
import com.example.Comp1640.Service.ChatMessageService;
import com.example.Comp1640.Service.ServiceImpl.AddFriendService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private AddFriendService addFriendService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUserDto() {
        return ResponseEntity.ok(userService.getAllUserDto());
    }

    @PostMapping("/save_user")
    public ResponseEntity<UserDto> saveNew(@ModelAttribute DefinedUser definedUser, @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.saveUser(definedUser,file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@RequestBody(required = false) UserDto userDto ) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/getOldMessages/{user1Id}/{user2Id}")
    public List<WbsChatMessage> getOldMessages(@PathVariable Long user1Id,
                                                               @PathVariable Long user2Id
    ) {
        return chatMessageService.getOldMessages(user1Id,user2Id);
    }

    @GetMapping("/getRequestIReceived/{recipientId}")
    public List<AddFriendRequestDto> getRequestIReceived(@PathVariable Long recipientId
    ) {
        return addFriendService.getAddFriendRequestIReceived(recipientId);
    }


    @PostMapping("/findTutor")
    public List<UserDto> findTutor(@RequestBody(required = false) SearchStudentOrTutor searchStudentOrTutor)
     {
//         System.out.println(searchStudentOrTutor.getUsername());
//         System.out.println(searchStudentOrTutor.getDistrictId());
//         System.out.println(searchStudentOrTutor.getGradeId());
//         System.out.println(searchStudentOrTutor.getMajorId());

         return userService.findTutor(searchStudentOrTutor);
     }

    @PostMapping("/findStudent")
    public List<UserDto> findStudent(@RequestBody(required = false) SearchStudentOrTutor searchStudentOrTutor)
    {
        return userService.findStudent(searchStudentOrTutor);
    }

}
