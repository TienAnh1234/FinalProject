package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.GradeDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Entity.Grade;
import com.example.Comp1640.Repository.GradeRepository;
import com.example.Comp1640.Service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public List<GradeDto> getAllGradeDto() {
        List<Grade> grades = gradeRepository.findAll();
        List<GradeDto> gradeDtos = new ArrayList<>();

        if (!grades.isEmpty()) {
            for(int i = 0; i < grades.size(); i++){
                GradeDto gradeDto = new GradeDto(grades.get(i).getId(), grades.get(i).getName());
                gradeDtos.add(gradeDto);
            }
            return gradeDtos;
        } else {
            return null;
        }
    }

}


