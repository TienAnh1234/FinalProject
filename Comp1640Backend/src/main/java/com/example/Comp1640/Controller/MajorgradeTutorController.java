package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.MajorGradeDto;
import com.example.Comp1640.DTO.MajorgradeTutorDto;
import com.example.Comp1640.DTO.PopularSubjectProjection;
import com.example.Comp1640.Service.MajorGradeService;
import com.example.Comp1640.Service.MajorgradeTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/majorgradeTutor")
public class MajorgradeTutorController {

    @Autowired
    private MajorgradeTutorService majorgradeTutorService;

    @PostMapping("/save_majorgradeTutor")
    public ResponseEntity<Boolean> saveNew(@RequestBody(required = false) List<MajorgradeTutorDto> majorgradeTutorDtos) {
        return ResponseEntity.ok(majorgradeTutorService.saveMajorgradeTutor(majorgradeTutorDtos));
    }

    @GetMapping("/{tutorId}")
    public ResponseEntity<List<MajorgradeTutorDto>> getAllMajorgradeTutorDtoByTutorId(@PathVariable Long tutorId) {
        return ResponseEntity.ok(majorgradeTutorService.getAllMajorgradeTutorDtoByTutorId(tutorId));
    }

    @PostMapping("/delete_majorgradeTutor")
    public void deleteMajorgradeTutor(@RequestBody(required = false) List<MajorgradeTutorDto> majorgradeTutorDtos) {
        System.out.println("Đã gọi vào endpoint delete_majorgradeTutor");

        majorgradeTutorService.deleteMajorgradeTutor(majorgradeTutorDtos);
    }


}
