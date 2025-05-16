package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.AddFriendRequestDto;
import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.AddFriendRequest;
import com.example.Comp1640.Service.ClassroomService;
import com.example.Comp1640.Service.ServiceImpl.AddFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private AddFriendService addFriendService;


    @GetMapping()
    public ResponseEntity<List<ClassroomDto>> getAllClassroomDto() {
        return ResponseEntity.ok(classroomService.getAllClassroomDto());
    }

    @PostMapping("/save_classroom")
    public ResponseEntity<String> saveNew(@RequestBody(required = false) ClassroomDto classroomDto) {
        return ResponseEntity.ok(classroomService.saveClassroom(classroomDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody(required = false) ClassroomDto classroomDto ) {
        return ResponseEntity.ok(classroomService.updateClassroom(classroomDto));
    }

    @DeleteMapping("/delete/{tutorId}")
    public void deleteClassroom(@PathVariable Long tutorId) {
        classroomService.deleteClassroom(tutorId);
    }

    @DeleteMapping("/delete/{tutorId}/{studentId}")
    public void deleteClassroomByStudentIdAndTutorId(@PathVariable Long tutorId,@PathVariable Long studentId) {
        classroomService.deleteClassroomByStudentIdAndTutorId(tutorId,studentId);
    }

    @PostMapping("/save_classroomMain")
    public ResponseEntity<String> saveNewMain(@RequestBody(required = false) AddFriendRequestDto addFriendRequestDto) {
        return ResponseEntity.ok(classroomService.saveClassroomMain(addFriendRequestDto));
    }

    @DeleteMapping("/deleteFriendRequest/{id}")
    public void deleteFriendRequest(@PathVariable Long id) {
        addFriendService.deleteAddFriendRequest(id);
    }

    @GetMapping("/getAllFriendRequest")
    public ResponseEntity<List<AddFriendRequest>> getAllFriendrequest() {
        return ResponseEntity.ok(classroomService.getAllAddFriendRequest());
    }

}
