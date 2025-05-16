package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudentDto();
    String updateStudent(Long id, StudentDto studentDto, MultipartFile file) throws IOException;



}
