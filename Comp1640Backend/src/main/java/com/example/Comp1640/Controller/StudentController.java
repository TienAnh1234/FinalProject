package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.Service.StudentService;
import com.example.Comp1640.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public ResponseEntity<List<StudentDto>> getAllStudentDto(HttpServletRequest request) {
        List<StudentDto> studentDtos = studentService.getAllStudentDto();
        if(!(studentDtos == null)) {
//            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//            for (StudentDto studentDto : studentDtos) {
//                studentDto.setImageFile(baseUrl + studentDto.getImageFile());
//            }
            return ResponseEntity.ok(studentDtos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @ModelAttribute StudentDto studentDto, @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {
        System.out.println(studentDto.getUsername() + " 123" );
        System.out.println(studentDto.getBirthday() + " 123" );
        System.out.println(studentDto.getName() + " 123" );
        System.out.println(studentDto.getImageFile() + " 123" );
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto,file));
    }


}
