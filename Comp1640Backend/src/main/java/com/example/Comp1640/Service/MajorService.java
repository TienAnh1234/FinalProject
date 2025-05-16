package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MajorService {


    Optional<Major> findMajorById(Long id);
    Long findIdByMajor(String majorName);

    List<MajorDto> getAllMajorDto();
    MajorDto saveMajor(MajorDto majorDto);
    void deleteMajor(Long id);
    MajorDto updateMajor(Long id, MajorDto majorDto);



}
