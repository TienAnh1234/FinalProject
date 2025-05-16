package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Service.StudentService;
import com.example.Comp1640.Service.TutorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tutor")
public class TutorController {


    @Autowired
    private TutorService tutorService;

    @GetMapping()
    public ResponseEntity<List<TutorDto>> getAllTutorDto(HttpServletRequest request) {
        List<TutorDto> tutorDtos = tutorService.getAllTutorDto();
        if(!(tutorDtos == null)) {
//            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//            for (TutorDto tutorDto : tutorDtos) {
//                tutorDto.setImageFile(baseUrl + tutorDto.getImageFile());
//            }
            return ResponseEntity.ok(tutorDtos);
        }else{
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @ModelAttribute TutorDto tutorDto, @RequestParam(value = "file",required = false) MultipartFile file ) throws IOException {

        return ResponseEntity.ok(tutorService.updateTutor(id, tutorDto,file));
    }


}
