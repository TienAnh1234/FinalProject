package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.MajorGradeDto;
import com.example.Comp1640.Entity.*;
import com.example.Comp1640.Repository.MajorGradeRepository;
import com.example.Comp1640.Service.MajorGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MajorGradeServiceImpl implements MajorGradeService {

    @Autowired
    private MajorGradeRepository majorGradeRepository;

    @Override
    public List<MajorGradeDto> getAllMajorGradeDto() {

        List<MajorGrade> majorGrades = majorGradeRepository.findAll();
        List<MajorGradeDto> majorGradeDtos = new ArrayList<>();

        if (majorGrades.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < majorGrades.size(); i++){
                MajorGradeDto majorGradeDto = new MajorGradeDto(majorGrades.get(i).getId(),majorGrades.get(i).getMajor().getId(),majorGrades.get(i).getGrade().getId());
                majorGradeDtos.add(majorGradeDto);
            }
        }
        return majorGradeDtos;
    }
}
