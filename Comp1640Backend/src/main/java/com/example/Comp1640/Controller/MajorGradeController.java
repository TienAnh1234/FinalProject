package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.GradeDto;
import com.example.Comp1640.DTO.MajorGradeDto;
import com.example.Comp1640.Service.GradeService;
import com.example.Comp1640.Service.MajorGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/majorGrade")
public class MajorGradeController {

    @Autowired
    private MajorGradeService majorGradeService;

    @GetMapping()
    public ResponseEntity<List<MajorGradeDto>> getAllGradeDto() {
        return ResponseEntity.ok(majorGradeService.getAllMajorGradeDto());
    }


}
