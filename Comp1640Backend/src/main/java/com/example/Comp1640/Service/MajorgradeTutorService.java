package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.MajorgradeTutorDto;
import com.example.Comp1640.DTO.PopularSubjectProjection;

import java.util.List;

public interface MajorgradeTutorService {

    boolean saveMajorgradeTutor( List<MajorgradeTutorDto> majorgradeTutorDtos);

    List<MajorgradeTutorDto> getAllMajorgradeTutorDtoByTutorId(Long tutorId);

    void deleteMajorgradeTutor( List<MajorgradeTutorDto> majorgradeTutorDtos);

    List<PopularSubjectProjection> getMostPopularSubject();
}
