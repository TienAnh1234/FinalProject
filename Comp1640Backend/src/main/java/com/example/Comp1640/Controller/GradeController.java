package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.DTO.GradeDto;
import com.example.Comp1640.Service.BlogService;
import com.example.Comp1640.Service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping()
    public ResponseEntity<List<GradeDto>> getAllGradeDto() {
        return ResponseEntity.ok(gradeService.getAllGradeDto());
    }

}
