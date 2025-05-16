package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.AddFriendRequestDto;
import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.ClassroomDtoMain;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.AddFriendRequest;

import java.util.List;

public interface ClassroomService {

    List<ClassroomDto> getAllClassroomDto();
    String saveClassroom(ClassroomDto classroomDto);
    String updateClassroom(ClassroomDto classroomDto);
    void deleteClassroom(Long tutorId);

    void deleteClassroomByStudentIdAndTutorId(Long tutorId,Long studentId);


    List<AddFriendRequest> getAllAddFriendRequest();
    String saveClassroomMain(AddFriendRequestDto addFriendRequestDto);
}
