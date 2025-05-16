package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorGradeDto;
import com.example.Comp1640.DTO.MajorgradeTutorDto;
import com.example.Comp1640.DTO.PopularSubjectProjection;
import com.example.Comp1640.Entity.MajorGrade;
import com.example.Comp1640.Entity.MajorgradeTutor;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.MajorGradeRepository;
import com.example.Comp1640.Repository.MajorgradeTutorRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.MajorgradeTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MajorgradeTutorServiceImpl implements MajorgradeTutorService {

    @Autowired
    private MajorgradeTutorRepository majorgradeTutorRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private MajorGradeRepository majorGradeRepository;

    @Override
    public boolean saveMajorgradeTutor(List<MajorgradeTutorDto> majorgradeTutorDtos) {
        Tutor tutor;
        MajorGrade majorgrade;

        if (majorgradeTutorDtos == null) {
            return false;
        }else{
            System.out.println(majorgradeTutorDtos.size() + " hanpi");

            for(int i = 0 ; i < majorgradeTutorDtos.size() ; i++){
                if(majorgradeTutorRepository.countExisted(majorgradeTutorDtos.get(i).getMajorGradeId(), majorgradeTutorDtos.get(i).getTutorId()) >0){
                    System.out.println("Co cai nay roiiii");
                }else{
                    tutor = tutorRepository.findById(majorgradeTutorDtos.get(i).getTutorId()).orElse(null);
                    majorgrade = majorGradeRepository.findById(majorgradeTutorDtos.get(i).getMajorGradeId()).orElse(null);
                    majorgradeTutorRepository.save(new MajorgradeTutor(majorgrade, tutor));
                }

            }
            return true;
        }
    }

    @Override
    public List<MajorgradeTutorDto> getAllMajorgradeTutorDtoByTutorId(Long tutorId) {

        List<MajorgradeTutor> majorGradeTutors = majorgradeTutorRepository.findByTutorId(tutorId);
        List<MajorgradeTutorDto> majorGradeTutorDtos = new ArrayList<>();

        if (majorGradeTutors.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < majorGradeTutors.size(); i++){
                MajorgradeTutorDto majorgradeTutorDto = new MajorgradeTutorDto(majorGradeTutors.get(i).getId(),majorGradeTutors.get(i).getMajorGrade().getId(),majorGradeTutors.get(i).getTutor().getId());
                majorGradeTutorDtos.add(majorgradeTutorDto);
            }
        }
        return majorGradeTutorDtos;
    }

    @Override
    public void deleteMajorgradeTutor(List<MajorgradeTutorDto> majorgradeTutorDtos) {
        for(int i = 0 ; i < majorgradeTutorDtos.size() ; i++){
            Optional<MajorgradeTutor> optional = majorgradeTutorRepository.findByMajorGradeIdAndTutorId(majorgradeTutorDtos.get(i).getMajorGradeId(), majorgradeTutorDtos.get(i).getTutorId());
            optional.ifPresent(majorgradeTutorRepository::delete);
        }
    }

    @Override
    public List<PopularSubjectProjection> getMostPopularSubject() {
        return majorgradeTutorRepository.findMostPopularMajorWithGrade();
    }


}
